package com.gabrielnilsonespindola.salesSystem.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielnilsonespindola.salesSystem.repositories.ClientRepository;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@Autowired
	private ClientRepository clientRepository;

}
