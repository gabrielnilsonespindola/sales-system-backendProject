package com.gabrielnilsonespindola.salesSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielnilsonespindola.salesSystem.entities.ProductSale;

public interface ProductRepository extends JpaRepository<ProductSale, Long> {

}
