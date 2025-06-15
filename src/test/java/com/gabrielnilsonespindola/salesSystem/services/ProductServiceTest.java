package com.gabrielnilsonespindola.salesSystem.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.gabrielnilsonespindola.salesSystem.dto.ProductDTO;
import com.gabrielnilsonespindola.salesSystem.entities.Product;
import com.gabrielnilsonespindola.salesSystem.repositories.ProductRepository;
import com.gabrielnilsonespindola.salesSystem.services.exceptions.ObjectNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@Mock
	ProductRepository productRepository;

	@InjectMocks
	ProductService productService;

	@Nested
	class findAll {

		@Test
		void findAllTestProductsOk() {

			// ARRANGE

			Product prod1 = new Product(1L, "Geladeira", 500.00, 10);
			Product prod2 = new Product(2L, "Fogao", 200.00, 5);

			List<Product> ListAll = List.of(prod1, prod2);
			when(productRepository.findAll()).thenReturn(ListAll);

			// ACTION

			List<Product> listAllFull = productService.findAll();

			// ASSERT

			assertNotNull(listAllFull);
			assertEquals(2, listAllFull.size());
			assertEquals("Geladeira", listAllFull.get(0).getName());
			assertEquals("Fogao", listAllFull.get(1).getName());
			verify(productRepository, times(1)).findAll();

		}

	}

	@Nested
	class findById {

		@Test
		void findByIdOk() {

			// ARRANGE
			Product product1 = new Product(1L, "Geladeira", 500.00, 10);
			Product product2 = new Product(2L, "Fogao", 200.00, 5);

			when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
			when(productRepository.findById(2L)).thenReturn(Optional.of(product2));

			// ACTION
			Product listById1 = productService.findById(1L);
			Product listById2 = productService.findById(2L);

			// ASSERT

			assertNotNull(listById1);
			assertNotNull(listById2);
			assertEquals(1L, listById1.getId());
			assertEquals(2L, listById2.getId());
			verify(productRepository, times(1)).findById(1L);
			verify(productRepository, times(1)).findById(2L);

		}

		@Test
		void findByIdNotOk() {

			// ARRANGE

			when(productRepository.findById(99L)).thenReturn(Optional.empty());

			// ACTION + ASSERT

			assertThrows(ObjectNotFoundException.class, () -> productService.findById(99L));
			verify(productRepository, times(1)).findById(99L);

		}

	}

	@Nested
	class insertProduct {

		@Test
		void insertProductOk() {

			// ARRANGE

			Product product1 = new Product(1L, "Geladeira", 500.00, 10);
			Product product2 = new Product(2L, "Fogao", 200.00, 5);

			when(productRepository.save(product1)).thenReturn(product1);
			when(productRepository.save(product2)).thenReturn(product2);

			// ACTION

			Product productNew1 = productService.insertProduct(product1);
			Product productNew2 = productService.insertProduct(product2);

			// ASSERT

			assertNotNull(productNew1);
			assertNotNull(productNew2);
			assertEquals(1L, productNew1.getId());
			assertEquals(2L, productNew2.getId());
			assertEquals("Geladeira", productNew1.getName());
			assertEquals(200.00, productNew2.getPrice());
			verify(productRepository, times(1)).save(product1);
			verify(productRepository, times(1)).save(product2);

		}
	}

	@Nested
	class fromDTO {

		@Test
		void fromDTOOk() {

			// ARRANGE

			ProductDTO dto = new ProductDTO();
			dto.setId(1L);
			dto.setName("Geladeira");
			dto.setPrice(500.00);
			dto.setStockQuantity(10);

			// ACTION

			Product product = productService.fromDTO(dto);

			// ASSERT

			assertNotNull(product);
			assertEquals(dto.getId(), product.getId());
			assertEquals(dto.getName(), product.getName());
			assertEquals(dto.getPrice(), product.getPrice());
			assertEquals(dto.getStockQuantity(), product.getStockQuantity());

		}
	}

}
