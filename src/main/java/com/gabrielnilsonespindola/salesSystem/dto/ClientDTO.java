package com.gabrielnilsonespindola.salesSystem.dto;

import java.io.Serializable;

public class ClientDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Integer cpf;
	private String email;
	
	public ClientDTO() {
	}

	public ClientDTO(Long id, String name, Integer cpf, String email) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.email = email;
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

	public Integer getCpf() {
		return cpf;
	}

	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
