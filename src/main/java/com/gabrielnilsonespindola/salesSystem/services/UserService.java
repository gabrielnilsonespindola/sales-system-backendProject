package com.gabrielnilsonespindola.salesSystem.services;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.gabrielnilsonespindola.salesSystem.dto.UserDTO;
import com.gabrielnilsonespindola.salesSystem.entities.Role;
import com.gabrielnilsonespindola.salesSystem.entities.User;
import com.gabrielnilsonespindola.salesSystem.repositories.RoleRepository;
import com.gabrielnilsonespindola.salesSystem.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User newUser(UserDTO dto) {

		var basicRole = roleRepository.findByName(Role.Values.basic.name());

		var userFromDb = userRepository.findByUsername(dto.getUsername());
		if (userFromDb.isPresent()) {
			log.warn("Tentativa de cadastro de usuario com USERNAME ja existente. {}" , dto.getUsername());
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		var user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setUsername(dto.getUsername());
		user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
		user.setRoles(Set.of(basicRole));

		return userRepository.save(user);

	}

}
