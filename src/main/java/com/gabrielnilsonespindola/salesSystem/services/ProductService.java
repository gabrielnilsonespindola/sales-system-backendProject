package com.gabrielnilsonespindola.salesSystem.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gabrielnilsonespindola.salesSystem.dto.ProductDTO;
import com.gabrielnilsonespindola.salesSystem.entities.Product;
import com.gabrielnilsonespindola.salesSystem.repositories.ProductRepository;
import com.gabrielnilsonespindola.salesSystem.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product findById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public Product insertProduct(Product obj) {
		return productRepository.save(obj);
	}

	public Product fromDTO(ProductDTO objDtO) {
		return new Product(objDtO.getId(), objDtO.getName(), objDtO.getPrice(), objDtO.getStockQuantity());
	}

}
