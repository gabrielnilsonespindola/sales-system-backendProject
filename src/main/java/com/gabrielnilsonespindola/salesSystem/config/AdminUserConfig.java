package com.gabrielnilsonespindola.salesSystem.config;

import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.gabrielnilsonespindola.salesSystem.entities.User;
import com.gabrielnilsonespindola.salesSystem.entities.Role;
import com.gabrielnilsonespindola.salesSystem.repositories.RoleRepository;
import com.gabrielnilsonespindola.salesSystem.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

	private RoleRepository roleRepository;
	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		var roleAdmin = roleRepository.findByName(Role.Values.admin.name());

		var userAdmin = userRepository.findByUsername("admin");

		userAdmin.ifPresentOrElse(user -> {
			System.out.println("admin ja existe");
		}, () -> {
			var user = new User();
			user.setUsername("admin");
			user.setPassword(bCryptPasswordEncoder.encode("123"));
			user.setRoles(Set.of(roleAdmin));
			userRepository.save(user);
			}
		);
	}
}