package com.gabrielnilsonespindola.salesSystem.resources;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.gabrielnilsonespindola.salesSystem.dto.ProductSaleDTO;
import com.gabrielnilsonespindola.salesSystem.entities.Order;
import com.gabrielnilsonespindola.salesSystem.services.OrderService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

	@Autowired
	private OrderService orderService;

	@GetMapping
	@PreAuthorize("hasAuthority('SCOPE_admin') or hasAuthority('SCOPE_basic') ")
	public ResponseEntity<List<Order>> findAll() {
		List<Order> list = orderService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('SCOPE_admin') or hasAuthority('SCOPE_basic') ")
	public ResponseEntity<Order> findById(@PathVariable Long id) {
		Order obj = orderService.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('SCOPE_admin') or hasAuthority('SCOPE_basic') ")
	@Transactional
	public ResponseEntity<ProductSaleDTO> newOrder(@RequestBody ProductSaleDTO dto) {
		Order obj = orderService.newOrder(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
