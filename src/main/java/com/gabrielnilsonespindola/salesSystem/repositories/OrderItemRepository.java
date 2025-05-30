package com.gabrielnilsonespindola.salesSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielnilsonespindola.salesSystem.entities.OrderItem;
import com.gabrielnilsonespindola.salesSystem.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
