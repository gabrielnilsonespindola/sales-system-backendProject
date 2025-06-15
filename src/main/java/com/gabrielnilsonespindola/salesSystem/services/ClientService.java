package com.gabrielnilsonespindola.salesSystem.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gabrielnilsonespindola.salesSystem.dto.ClientDTO;
import com.gabrielnilsonespindola.salesSystem.entities.Client;
import com.gabrielnilsonespindola.salesSystem.repositories.ClientRepository;
import com.gabrielnilsonespindola.salesSystem.services.exceptions.ObjectNotFoundException;
import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;
import jakarta.transaction.Transactional;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	CPFValidator cpfValidator;

	public List<Client> findAll() {
		return clientRepository.findAll();
	}

	public Client findById(Long id) {
		Optional<Client> obj = clientRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public Client insertClient(Client obj) {
		return clientRepository.save(obj);
	}

	public Client fromDTO(ClientDTO objDtO) {
		return new Client(objDtO.getId(), objDtO.getName(), objDtO.getCpf(), objDtO.getEmail());
	}

	@Transactional
	public boolean validation(String cpf) {

		List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf);
		if (erros.size() > 0) {
			throw new ObjectNotFoundException("CPF INVALIDO");
		} else {
			return true;
		}

	}

	public Client registerClient(ClientDTO objDtO, String cpf) {

		var cpfFromDb = clientRepository.findByCpf(objDtO.getCpf());

		if (!validation(cpf) && cpfFromDb.isPresent()) {
			throw new ObjectNotFoundException("CPF inválido ou ja cadastrado, registro não permitido.");
		} else {
			Client client = new Client();
			client.setName(objDtO.getName());
			client.setCpf(objDtO.getCpf());
			client.setEmail(objDtO.getEmail());
			return clientRepository.save(client);
		}
	}

}
