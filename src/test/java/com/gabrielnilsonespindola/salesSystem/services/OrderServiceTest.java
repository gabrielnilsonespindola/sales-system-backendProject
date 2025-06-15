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

	@Captor
	private ArgumentCaptor<Order> orderCaptor;

	@InjectMocks
	OrderService orderService;

	@Nested
	class findAll {

		@Test
		void findAllTestOrdersOk() {

			// ARRANGE

			Product product1 = new Product();
			product1.setId(1L);

			Client client1 = new Client();
			client1.setId(1L);

			Order order1 = new Order();
			order1.setId(1L);
			order1.setTotalValue(500.00);
			order1.getClient();
			order1.getProducts();

			Order order2 = new Order();
			order2.setId(2L);
			order2.setTotalValue(500.00);
			order2.getClient();

			List<Order> listAll = List.of(order1, order2);
			when(orderRepository.findAll()).thenReturn(listAll);

			// ACTION

			List<Order> listAllFull = orderService.findAll();

			// ASSERT

			assertNotNull(listAllFull);
			assertEquals(2, listAllFull.size());
			assertEquals(1, listAllFull.get(0).getId());
			assertEquals(2, listAllFull.get(1).getId());
			verify(orderRepository, times(1)).findAll();

		}

	}

	@Nested
	class findById {

		@Test
		void findByIdOk() {

			// ARRANGE

			Product product1 = new Product();
			product1.setId(1L);

			Client client1 = new Client();
			client1.setId(1L);

			Order order1 = new Order();
			order1.setId(1L);

			Order order2 = new Order();
			order2.setId(2L);

			when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));
			when(orderRepository.findById(2L)).thenReturn(Optional.of(order2));

			// ACTION

			Order listById1 = orderService.findById(1L);
			Order listById2 = orderService.findById(2L);

			// ASSERT

			assertNotNull(listById1);
			assertNotNull(listById2);
			assertEquals(1L, listById1.getId());
			assertEquals(2L, listById2.getId());
			verify(orderRepository, times(1)).findById(1L);
			verify(orderRepository, times(1)).findById(2L);

		}

		@Test
		void findByIdNotOk() {

			// ARRANGE

			Order order1 = new Order();
			order1.setId(1L);

			Order order2 = new Order();
			order2.setId(2L);

			when(orderRepository.findById(99L)).thenReturn(Optional.empty());

			// ACTION + ASSERT

			assertThrows(ObjectNotFoundException.class, () -> orderService.findById(99L));
			verify(orderRepository, times(1)).findById(99L);

		}

	}

	@Nested
	class newOrder {

		@Test
		void ProductNotFoundAndOrNoCustomerRegistered() {

			// ARRANGE

			ProductSaleDTO productSaleDTO = new ProductSaleDTO();
			productSaleDTO.setProductId(99L);
			productSaleDTO.setClientId(99L);

			Product product = new Product();
			product.setId(productSaleDTO.getProductId());

			Client client = new Client();
			client.setId(productSaleDTO.getClientId());

			when(productRepository.findById(99L)).thenReturn(Optional.empty());
			when(clientRepository.findById(99L)).thenReturn(Optional.empty());

			// ACTION + ASSERT

			assertThrows(ObjectNotFoundException.class, () -> orderService.newOrder(productSaleDTO));
			verify(productRepository, times(1)).findById(99L);
			verify(clientRepository, times(1)).findById(99L);

		}

		@Test
		void ProductNotFoundAndExceptionIsThrown() {

			// ARRANGE

			ProductSaleDTO productSaleDTO = new ProductSaleDTO();
			productSaleDTO.setProductId(99L);
			productSaleDTO.setClientId(99L);

			Product product = new Product();
			product.setId(productSaleDTO.getProductId());

			Client client = new Client();
			client.setId(productSaleDTO.getClientId());

			when(productRepository.findById(99L)).thenReturn(Optional.empty());

			// ACTION + ASSERT

			assertThrows(ObjectNotFoundException.class, () -> orderService.newOrder(productSaleDTO));
			verify(productRepository, times(1)).findById(99L);

		}

		@Test
		void NoCustomerRegisteredAndExceptionIsThrown() {

			// ARRANGE

			ProductSaleDTO productSaleDTO = new ProductSaleDTO();
			productSaleDTO.setProductId(99L);
			productSaleDTO.setClientId(99L);

			Product product = new Product();
			product.setId(productSaleDTO.getProductId());

			Client client = new Client();
			client.setId(productSaleDTO.getClientId());

			when(clientRepository.findById(99L)).thenReturn(Optional.empty());

			// ACTION + ASSERT

			assertThrows(ObjectNotFoundException.class, () -> orderService.newOrder(productSaleDTO));
			verify(clientRepository, times(1)).findById(99L);

		}

		@Test
		void quantityInStockUnavailable() {

			// ARRANGE

			ProductSaleDTO productSaleDTO = new ProductSaleDTO();
			productSaleDTO.setProductId(1L);
			productSaleDTO.setStockQuantity(3);

			Product product = new Product();
			product.setId(productSaleDTO.getProductId());
			product.setStockQuantity(null);

			when(productRepository.findById(1L)).thenReturn(Optional.of(product));

			// ACTION + ASSERT

			assertThrows(ObjectNotFoundException.class, () -> orderService.newOrder(productSaleDTO));
			verify(productRepository, times(1)).findById(1L);

		}

		@Test
		void registerNewOrderSaveRepositoryIfProductIsPresentinStockAndCustomerRegistered() {

			// ARRANGE

			ProductSaleDTO productSaleDTO = new ProductSaleDTO();
			productSaleDTO.setProductId(1L);
			productSaleDTO.setClientId(1L);
			productSaleDTO.setStockQuantity(5);

			Product product = new Product();
			product.setId(productSaleDTO.getProductId());
			product.setPrice(100.00);
			product.setStockQuantity(10);

			Client client = new Client();
			client.setId(productSaleDTO.getClientId());

			Order order = new Order();
			order.setClient(client);
			order.getProducts().add(product);
			order.setTotalValue(product.getPrice() * productSaleDTO.getStockQuantity());
			order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
			order.setMoment(Instant.now());

			when(productRepository.findById(1L)).thenReturn(Optional.of(product));
			when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
			when(orderRepository.save(order)).thenReturn(order);

			// ACTION

			Order newOrder = orderService.newOrder(productSaleDTO);

			// ASSERT

			assertNotNull(newOrder);
			verify(orderRepository).save(orderCaptor.capture());
			Order capturedOrder = orderCaptor.getValue();
			assertTrue(capturedOrder.getTotalValue().equals(order.getTotalValue()));
			verify(productRepository, times(1)).findById(1L);
			verify(clientRepository, times(1)).findById(1L);
			verify(orderRepository, times(1)).save(capturedOrder);

		}
	}

}
