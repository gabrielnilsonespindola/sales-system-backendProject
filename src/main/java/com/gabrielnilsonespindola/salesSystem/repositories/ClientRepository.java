package com.gabrielnilsonespindola.salesSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielnilsonespindola.salesSystem.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
