package com.gabrielnilsonespindola.salesSystem.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gabrielnilsonespindola.salesSystem.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
