package com.gabrielnilsonespindola.salesSystem.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
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
import com.gabrielnilsonespindola.salesSystem.dto.ClientDTO;
import com.gabrielnilsonespindola.salesSystem.entities.Client;
import com.gabrielnilsonespindola.salesSystem.services.ClientService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService clientService;

	@GetMapping
	@PreAuthorize("hasAuthority('SCOPE_admin') or hasAuthority('SCOPE_basic') ")
	public ResponseEntity<List<ClientDTO>> findAll() {
		log.info("Inicio do Metodo findAll");
		List<Client> list = clientService.findAll();
		List<ClientDTO> listDto = list.stream().map(x -> new ClientDTO(x)).collect(Collectors.toList());
		log.info("Retorno metodo findAll");
		log.info("Final do metodo findAll");
		return ResponseEntity.ok().body(listDto);
	}

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('SCOPE_admin') or hasAuthority('SCOPE_basic') ")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
		log.info("Inicio do Metodo findById");
		Client obj = clientService.findById(id);
		log.info("Retorno metodo findById {}",id);		
		log.info("Final do metodo findById");
		return ResponseEntity.ok().body(new ClientDTO(obj));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('SCOPE_admin') or hasAuthority('SCOPE_basic') ")
	@Transactional
	public ResponseEntity<ClientDTO> registerClient(@RequestBody ClientDTO dto, String cpf) {
		log.info("Inicio do Metodo registerClient");
		String cpfObj = dto.getCpf();
		Client obj = clientService.registerClient(dto, cpfObj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		log.info("Retorno metodo registerClient {}",uri);
		log.info("Final do metodo registerClient, REGISTRO EFETUADO COM SUCESSO");
		return ResponseEntity.created(uri).build();
	}

}
