package com.gabrielnilsonespindola.salesSystem.dto;

import java.io.Serializable;
import java.time.Instant;
import com.gabrielnilsonespindola.salesSystem.entities.Client;
import com.gabrielnilsonespindola.salesSystem.entities.Order;
import com.gabrielnilsonespindola.salesSystem.entities.Product;
import com.gabrielnilsonespindola.salesSystem.entities.enums.OrderStatus;

public class ProductSaleDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Instant moment;
	private Double price;
	private Integer stockQuantity;
	private Long clientId;
	private Long orderId;
	private Long productId;
	private OrderStatus orderStatus;
	private Double totalValue;

	public ProductSaleDTO() {
	}

	public ProductSaleDTO(Order order, Client client, Product product, OrderStatus orderStatus) {
		this.moment = moment;
		clientId = client.getId();
		orderId = order.getId();
		productId = product.getId();
		stockQuantity = product.getStockQuantity();
		price = product.getPrice();
		totalValue = order.getTotalValue();
		this.orderStatus = orderStatus;

	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

}