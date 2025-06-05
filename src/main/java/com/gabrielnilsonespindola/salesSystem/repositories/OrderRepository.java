package com.gabrielnilsonespindola.salesSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gabrielnilsonespindola.salesSystem.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
