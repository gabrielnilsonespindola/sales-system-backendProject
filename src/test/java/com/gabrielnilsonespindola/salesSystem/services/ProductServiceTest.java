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

			var prod1 = new Product(1L, "Geladeira", 500.00, 10);
			var prod2 = new Product(2L, "Fogao", 200.00, 5);

			List<Product> ListAll = List.of(prod1, prod2);
			when(productRepository.findAll()).thenReturn(ListAll);

			List<Product> listAllFull = productService.findAll();

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

			var product = new Product();
			product.setId(1L);

			when(productRepository.findById(1L)).thenReturn(Optional.of(product));
			var listById = productService.findById(1L);

			assertNotNull(listById);
			assertEquals(1L, listById.getId());
			verify(productRepository, times(1)).findById(1L);

		}

		@Test
		void findByIdNotOk() {

			when(productRepository.findById(99L)).thenReturn(Optional.empty());
			assertThrows(ObjectNotFoundException.class, () -> productService.findById(99L));
			verify(productRepository, times(1)).findById(99L);

		}

	}

	@Nested
	class insertProduct {

		@Test
		void insertProductOk() {

			var product = new Product(1L, "Geladeira", 500.00, 10);

			when(productRepository.save(product)).thenReturn(product);
			var productNew = productService.insertProduct(product);

			assertNotNull(productNew);
			assertEquals(1L, productNew.getId());
			assertEquals("Geladeira", productNew.getName());
			verify(productRepository, times(1)).save(product);

		}
	}
}
