package com.gabrielnilsonespindola.salesSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielnilsonespindola.salesSystem.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
