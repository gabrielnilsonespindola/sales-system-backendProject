package com.gabrielnilsonespindola.salesSystem.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gabrielnilsonespindola.salesSystem.dto.ProductSaleDTO;
import com.gabrielnilsonespindola.salesSystem.entities.Order;
import com.gabrielnilsonespindola.salesSystem.entities.enums.OrderStatus;
import com.gabrielnilsonespindola.salesSystem.repositories.ClientRepository;
import com.gabrielnilsonespindola.salesSystem.repositories.OrderRepository;
import com.gabrielnilsonespindola.salesSystem.repositories.ProductRepository;
import com.gabrielnilsonespindola.salesSystem.services.exceptions.ObjectNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ClientRepository clientRepository;


	@Autowired
	private ProductRepository productRepository;

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public Order findById(Long id) {
		Optional<Order> obj = orderRepository.findById(id);
		return obj.orElseThrow(() -> {
	        log.error("Order com ID {} não encontrado", id);
	        return new ObjectNotFoundException("Objeto não encontrado");
	    });
	}

	public Order newOrder(ProductSaleDTO objDTO) {

		var clientFromDb = clientRepository.findById(objDTO.getClientId());

		var productFromDb = productRepository.findById(objDTO.getProductId());

		if (productFromDb.isPresent() && clientFromDb.isPresent()) {
			
			var client = clientFromDb.get();
			var product = productFromDb.get();
			
			if(product.getStockQuantity() < 1) {	
			log.warn("Produto sem estoque, nao permitido criar ordem de serviço. {}" , product.getStockQuantity());	
			throw new ObjectNotFoundException("Quantidade em estoque indisponivel");				
			}			
			
			client.setId(objDTO.getClientId());
			product.getId();
			product.getPrice();
			product.getStockQuantity();

			var order = new Order();
			order.setClient(client);
			order.getProducts().add(product);

			order.setTotalValue(product.getPrice() * objDTO.getStockQuantity());
			order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
			order.setMoment(Instant.now());

			return orderRepository.save(order);				
		} 
		else {
			log.warn("Produto não localizado {} e/ou sem cliente cadastrado {}" , productFromDb.isPresent() , clientFromDb.isPresent());
			throw new ObjectNotFoundException("Produto não localizado e/ou sem cliente cadastrado");
		}	
	}	
}