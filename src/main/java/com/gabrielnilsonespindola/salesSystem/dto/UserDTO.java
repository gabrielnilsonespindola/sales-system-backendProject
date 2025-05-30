package com.gabrielnilsonespindola.salesSystem.dto;

import com.gabrielnilsonespindola.salesSystem.entities.User;

public class UserDTO {
	
	private Long id;
	private String name;
	private String email;
	private String password;
	
	
	public UserDTO() {
	}
	
	public UserDTO(User user) {
	 id = user.getId();
	 name = user.getName();
	 email = user.getEmail();
	 password = user.getPassword();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
