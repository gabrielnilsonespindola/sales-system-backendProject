package com.gabrielnilsonespindola.salesSystem.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gabrielnilsonespindola.salesSystem.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	Optional<Client> findById(Client clientId);

	Optional<Client> findByCpf(String cpf);

}
