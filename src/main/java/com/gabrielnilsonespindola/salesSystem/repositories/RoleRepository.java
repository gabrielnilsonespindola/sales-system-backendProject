package com.gabrielnilsonespindola.salesSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gabrielnilsonespindola.salesSystem.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(String name);

}
