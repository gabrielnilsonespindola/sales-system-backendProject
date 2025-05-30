package com.gabrielnilsonespindola.salesSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielnilsonespindola.salesSystem.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
