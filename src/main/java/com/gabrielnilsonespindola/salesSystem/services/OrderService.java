package com.gabrielnilsonespindola.salesSystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabrielnilsonespindola.salesSystem.dto.ProductSaleDTO;
import com.gabrielnilsonespindola.salesSystem.entities.Order;
import com.gabrielnilsonespindola.salesSystem.entities.OrderItem;
import com.gabrielnilsonespindola.salesSystem.entities.Product;
import com.gabrielnilsonespindola.salesSystem.entities.enums.OrderStatus;
import com.gabrielnilsonespindola.salesSystem.repositories.ClientRepository;
import com.gabrielnilsonespindola.salesSystem.repositories.OrderItemRepository;
import com.gabrielnilsonespindola.salesSystem.repositories.OrderRepository;
import com.gabrielnilsonespindola.salesSystem.repositories.ProductRepository;
import com.gabrielnilsonespindola.salesSystem.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ProductRepository productRepository;

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public Order findById(Long id) {
		Optional<Order> obj = orderRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public Order newOrder(ProductSaleDTO objDTO) {

		var clientFromDb = clientRepository.findById(objDTO.getClientId());

		var productFromDb = productRepository.findBystockQuantity(objDTO.getStockQuantity());

		if (productFromDb.isPresent() && clientFromDb.isPresent()) {

			var order = new Order();
			order.getMoment();
			order.setClient(objDTO.getClientId());
			
			var product = new Product();
			product.getId();
			product.getName();
			product.getPrice();
			product.getStockQuantity();
			
			var orderItem = new OrderItem();
			orderItem.setQuantity(objDTO.getQuantity());
			orderItem.setPrice(objDTO.getPrice());			

			order.setTotalValue(totalSale(objDTO));
			// order.setTotalValue(orderItem.getPrice() * orderItem.getQuantity());
			order.setOrderStatus(OrderStatus.WAITING_PAYMENT);			
			return orderRepository.save(order);
		} 
		else {
			throw new ObjectNotFoundException("Quantidade em estoque indisponivel e/ou sem cliente cadastrado");
		}

	}

	public double totalSale(ProductSaleDTO dto) {
		OrderItem obj = new OrderItem();
		var price = obj.getPrice();
		var quantity = obj.getQuantity();
		double total = price * quantity;
		return total;
	}

}
