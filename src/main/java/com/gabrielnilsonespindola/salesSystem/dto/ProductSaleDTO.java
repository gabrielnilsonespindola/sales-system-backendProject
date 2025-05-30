package com.gabrielnilsonespindola.salesSystem.dto;

import java.io.Serializable;

import com.gabrielnilsonespindola.salesSystem.entities.Order;
import com.gabrielnilsonespindola.salesSystem.entities.OrderItem;
import com.gabrielnilsonespindola.salesSystem.entities.Product;
import com.gabrielnilsonespindola.salesSystem.pk.OrderItemPK;

public class ProductSaleDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private OrderItemPK id;
	private Double price;
	private Integer quantity;
	private Order orderId;
	private Product productId;

	public ProductSaleDTO() {
	}

	public ProductSaleDTO(OrderItemPK orderItemPK, OrderItem orderItem) {
		id = orderItem.getId();
		price = orderItem.getPrice();
		quantity = orderItem.getQuantity();
		orderId = orderItemPK.getOrder();
		productId = orderItemPK.getProduct();

	}

	public OrderItemPK getId() {
		return id;
	}

	public void setId(OrderItemPK id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Order getOrderId() {
		return orderId;
	}

	public void setOrderId(Order orderId) {
		this.orderId = orderId;
	}

	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
		this.productId = productId;
	}
	
	
	
	

}
