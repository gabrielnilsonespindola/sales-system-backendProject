package com.gabrielnilsonespindola.salesSystem.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.gabrielnilsonespindola.salesSystem.dto.UserDTO;
import com.gabrielnilsonespindola.salesSystem.entities.User;
import com.gabrielnilsonespindola.salesSystem.services.UserService;
import jakarta.transaction.Transactional;

@RestController
public class UserResource {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = userService.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@PostMapping("/users")
	@Transactional
	public ResponseEntity<UserDTO> newUser(@RequestBody UserDTO dto) {
		User obj = userService.newUser(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

}
