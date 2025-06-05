package com.gabrielnilsonespindola.salesSystem.dto;

import java.io.Serializable;

import com.gabrielnilsonespindola.salesSystem.entities.Client;
import com.gabrielnilsonespindola.salesSystem.entities.Order;
import com.gabrielnilsonespindola.salesSystem.entities.OrderItem;
import com.gabrielnilsonespindola.salesSystem.entities.Product;
import com.gabrielnilsonespindola.salesSystem.entities.enums.OrderStatus;
import com.gabrielnilsonespindola.salesSystem.pk.OrderItemPK;

public class ProductSaleDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private OrderItemPK id;
	private Double price;
	private Integer quantity;
	private Integer stockQuantity;
	private Client clientId;
	private Order orderId;
	private Product productId;
	private OrderItem orderItem;
	private OrderStatus orderItemStatus;
	private Double totalValue;
	private String cpf;

	public ProductSaleDTO() {
	}

	public ProductSaleDTO(OrderItemPK orderItemPK, OrderItem orderItem, Order order) {
		id = orderItem.getId();
		price = orderItem.getPrice();
		quantity = orderItem.getQuantity();		
		orderId = orderItemPK.getOrder();
		productId = orderItemPK.getProduct();
		this.clientId = clientId;
		this.stockQuantity = stockQuantity;
		this.orderItemStatus = orderItemStatus;
		this.totalValue = totalValue;
		this.cpf = cpf;
		

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

	public Client getClientId() {
		return clientId;
	}

	public void setClientId(Client clientId) {
		this.clientId = clientId;
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

	public OrderStatus getOrderItemStatus() {
		return orderItemStatus;
	}

	public void setOrderItemStatus(OrderStatus orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	

}
