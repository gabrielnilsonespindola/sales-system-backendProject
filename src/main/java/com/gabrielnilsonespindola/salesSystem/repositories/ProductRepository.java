package com.gabrielnilsonespindola.salesSystem.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gabrielnilsonespindola.salesSystem.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findById(Long productId);

}
