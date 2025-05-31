package com.gabrielnilsonespindola.salesSystem.dto;

import java.io.Serializable;

import com.gabrielnilsonespindola.salesSystem.entities.Product;

public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Double price;
	private Integer stockQuantity;

	public ProductDTO() {
	}

	public ProductDTO(Product product) {
		id = product.getId();
		name = product.getName();
		price = product.getPrice();
		stockQuantity = product.getStockQuantity();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	

}
