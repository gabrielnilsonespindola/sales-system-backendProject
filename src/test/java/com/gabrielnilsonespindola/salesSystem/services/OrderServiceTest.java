package com.gabrielnilsonespindola.salesSystem.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.gabrielnilsonespindola.salesSystem.dto.ProductSaleDTO;
import com.gabrielnilsonespindola.salesSystem.entities.Client;
import com.gabrielnilsonespindola.salesSystem.entities.Order;
import com.gabrielnilsonespindola.salesSystem.entities.Product;
import com.gabrielnilsonespindola.salesSystem.entities.enums.OrderStatus;
import com.gabrielnilsonespindola.salesSystem.repositories.ClientRepository;
import com.gabrielnilsonespindola.salesSystem.repositories.OrderRepository;
import com.gabrielnilsonespindola.salesSystem.repositories.ProductRepository;
import com.gabrielnilsonespindola.salesSystem.services.exceptions.ObjectNotFoundException;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	@Mock
	OrderRepository orderRepository;

	@Mock
	ProductRepository productRepository;

	@Mock
	ClientRepository clientRepository;

	@InjectMocks
	OrderService orderService;

	@Captor
	private ArgumentCaptor<Order> orderCaptor;

	@Nested
	class findAll {

		@Test
		void findAllOrdersOk() {

			var order1 = new Order();
			order1.setId(1L);

			var order2 = new Order();
			order2.setId(2L);

			List<Order> listAllOrders = List.of(order1, order2);
			when(orderRepository.findAll()).thenReturn(listAllOrders);

			List<Order> listAllFullOrders = orderService.findAll();
			assertNotNull(listAllFullOrders);
			assertEquals(2, listAllFullOrders.size());
			assertEquals(1, listAllFullOrders.get(0).getId());
			assertEquals(2, listAllFullOrders.get(1).getId());
			verify(orderRepository, times(1)).findAll();
		}
	}

	@Nested
	class findById {

		@Test
		void findByIdOk() {

			var order = new Order();
			order.setId(1L);

			when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

			var listById = orderService.findById(1L);
			assertNotNull(listById);
			assertEquals(1L, listById.getId());
			verify(orderRepository, times(1)).findById(1L);
		}

		@Test
		void findByIdNotOk() {

			var order = new Order();
			order.setId(1L);

			when(orderRepository.findById(99L)).thenReturn(Optional.empty());
			assertThrows(ObjectNotFoundException.class, () -> orderService.findById(99L));
			verify(orderRepository, times(1)).findById(99L);
		}
	}

	@Nested
	class newOrder {

		@Test
		void ProductAndCustomerNotRegistred() {

			var productSaleDTO = new ProductSaleDTO();
			productSaleDTO.setProductId(99L);
			productSaleDTO.setClientId(99L);

			var product = new Product();
			product.setId(productSaleDTO.getProductId());

			var client = new Client();
			client.setId(productSaleDTO.getClientId());

			when(productRepository.findById(99L)).thenReturn(Optional.empty());
			when(clientRepository.findById(99L)).thenReturn(Optional.empty());
			assertThrows(ObjectNotFoundException.class, () -> orderService.newOrder(productSaleDTO));
			verify(productRepository, times(1)).findById(99L);
			verify(clientRepository, times(1)).findById(99L);
		}

		@Test
		void ProductNotFound() {

			var productSaleDTO = new ProductSaleDTO();
			productSaleDTO.setProductId(99L);
			productSaleDTO.setClientId(99L);

			var product = new Product();
			product.setId(productSaleDTO.getProductId());

			var client = new Client();
			client.setId(productSaleDTO.getClientId());

			when(productRepository.findById(99L)).thenReturn(Optional.empty());
			assertThrows(ObjectNotFoundException.class, () -> orderService.newOrder(productSaleDTO));
			verify(productRepository, times(1)).findById(99L);

		}

		@Test		
		void NoCustomerRegistered() {

			var productSaleDTO = new ProductSaleDTO();
			productSaleDTO.setProductId(99L);
			productSaleDTO.setClientId(99L);

			var product = new Product();
			product.setId(productSaleDTO.getProductId());

			var client = new Client();
			client.setId(productSaleDTO.getClientId());

			when(clientRepository.findById(99L)).thenReturn(Optional.empty());
			assertThrows(ObjectNotFoundException.class, () -> orderService.newOrder(productSaleDTO));
			verify(clientRepository, times(1)).findById(99L);

		}

		@Test		
		void quantityInStockUnavailable() {

			var productSaleDTO = new ProductSaleDTO();
			productSaleDTO.setProductId(1L);
			productSaleDTO.setStockQuantity(3);

			var product = new Product();
			product.setId(productSaleDTO.getProductId());
			product.setStockQuantity(null);

			when(productRepository.findById(1L)).thenReturn(Optional.of(product));
			assertThrows(ObjectNotFoundException.class, () -> orderService.newOrder(productSaleDTO));
			verify(productRepository, times(1)).findById(1L);
		}

		@Test		
		void registerNewOrderSaveRepository() {

			var productSaleDTO = new ProductSaleDTO();
			productSaleDTO.setProductId(1L);
			productSaleDTO.setClientId(1L);
			productSaleDTO.setStockQuantity(5);

			var product = new Product();
			product.setId(productSaleDTO.getProductId());
			product.setPrice(100.00);
			product.setStockQuantity(10);

			var client = new Client();
			client.setId(productSaleDTO.getClientId());

			var order = new Order();
			order.setClient(client);
			order.getProducts().add(product);
			order.setTotalValue(product.getPrice() * productSaleDTO.getStockQuantity());
			order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
			order.setMoment(Instant.now());

			when(productRepository.findById(1L)).thenReturn(Optional.of(product));
			when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
			when(orderRepository.save(order)).thenReturn(order);

			var newOrder = orderService.newOrder(productSaleDTO);
			assertNotNull(newOrder);
			verify(orderRepository).save(orderCaptor.capture());
			var capturedOrder = orderCaptor.getValue();
			assertTrue(capturedOrder.getTotalValue().equals(order.getTotalValue()));
			verify(productRepository, times(1)).findById(1L);
			verify(clientRepository, times(1)).findById(1L);
			verify(orderRepository, times(1)).save(capturedOrder);
		}
	}
}
