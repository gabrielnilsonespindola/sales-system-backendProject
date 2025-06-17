package com.gabrielnilsonespindola.salesSystem.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.gabrielnilsonespindola.salesSystem.dto.ClientDTO;
import com.gabrielnilsonespindola.salesSystem.entities.Client;
import com.gabrielnilsonespindola.salesSystem.repositories.ClientRepository;
import com.gabrielnilsonespindola.salesSystem.services.exceptions.ObjectNotFoundException;
import br.com.caelum.stella.validation.CPFValidator;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

	@Mock
	ClientRepository clientRepository;

	@InjectMocks
	ClientService clientService;

	@Mock
	CPFValidator cpfValidator;

	@Nested
	class findAll {

		@Test
		void findAllTestClientsOk() {

			var client1 = new Client(1L, "Jao", "09514530926", "jao@gmail.com");
			var client2 = new Client(2L, "Tao", "09514530926", "tao@gmail.com");

			List<Client> listAll = List.of(client1, client2);
			when(clientRepository.findAll()).thenReturn(listAll);

			List<Client> listAllFull = clientService.findAll();

			assertNotNull(listAllFull);
			assertEquals(2, listAllFull.size());
			verify(clientRepository, times(1)).findAll();

		}
	}

	@Nested
	class findById {

		@Test
		void findByIdOk() {

			var client1 = new Client(1L, "Jao", "09514530926", "jao@gmail.com");

			when(clientRepository.findById(1L)).thenReturn(Optional.of(client1));

			var listById1 = clientService.findById(1L);

			assertNotNull(listById1);
			assertEquals(1L, listById1.getId());
			verify(clientRepository, times(1)).findById(1L);

		}

		@Test
		void findByIdNotOk() {

			when(clientRepository.findById(99L)).thenReturn(Optional.empty());
			assertThrows(ObjectNotFoundException.class, () -> clientService.findById(99L));
			verify(clientRepository, times(1)).findById(99L);

		}

	}

	@Nested
	class validation {

		@Test
		void validationOk() {

			String cpf = "09514530926";
			when(cpfValidator.invalidMessagesFor(cpf)).thenReturn(Collections.emptyList());
			assertDoesNotThrow(() -> clientService.validation(cpf));
			verify(cpfValidator, times(1)).invalidMessagesFor(cpf);

		}

		@Test
		void validationNotOk() {

			String cpf = "09514599999";
			when(cpfValidator.invalidMessagesFor(cpf)).thenReturn(List.of(() -> ("CPF INVALIDO")));
			assertThrows(ObjectNotFoundException.class, () -> clientService.validation(cpf));
			verify(cpfValidator, times(1)).invalidMessagesFor(cpf);

		}
	}

	@Nested
	class registerClient {

		@Test
		void ValidationIsOkAndNotHaveCpfRegister() {

			String cpf = "09514530926";
			var dto = new ClientDTO();
			dto.setCpf(cpf);

			when(cpfValidator.invalidMessagesFor(cpf)).thenReturn(Collections.emptyList());
			when(clientRepository.findByCpf(cpf)).thenReturn(Optional.empty());

			assertDoesNotThrow(() -> clientService.registerClient(dto, cpf));
			verify(clientRepository, times(1)).findByCpf(cpf);
			verify(cpfValidator, times(1)).invalidMessagesFor(cpf);

		}

		@Test
		void NotRegisterClientIfValidationNotOk() {

			String cpf = "99999999999";
			var dto = new ClientDTO();
			dto.setCpf(cpf);
			when(cpfValidator.invalidMessagesFor(cpf)).thenReturn(List.of(() -> ("CPF INVALIDO")));

			assertThrows(ObjectNotFoundException.class, () -> clientService.registerClient(dto, cpf));
			verify(cpfValidator, times(1)).invalidMessagesFor(cpf);
		}

		@Test
		void NotRegisterClientIfCpfAlreadyExist() {

			String cpf = "09514530926";
			var dto = new ClientDTO();
			dto.setCpf(cpf);

			var client = new Client();
			client.setCpf(cpf);

			when(clientRepository.findByCpf(cpf)).thenReturn(Optional.of(client));
			when(cpfValidator.invalidMessagesFor(cpf)).thenReturn(List.of(() -> ("CPF INVALIDO")));

			assertThrows(ObjectNotFoundException.class, () -> clientService.registerClient(dto, cpf));
			verify(clientRepository, times(1)).findByCpf(cpf);
			verify(cpfValidator, times(1)).invalidMessagesFor(cpf);

		}

		@Test
		void registerClientSaveRepositoryOk() {

			String cpf = "09514530926";
			var dto = new ClientDTO();
			dto.setName("jao");
			dto.setCpf(cpf);
			dto.setEmail("jao@gmail.com");

			var client = new Client();
			client.setName(dto.getName());
			client.setCpf(dto.getCpf());
			client.setEmail(dto.getEmail());

			when(clientRepository.save(any(Client.class))).thenReturn(client);

			var newClient = clientService.registerClient(dto, cpf);

			assertNotNull(newClient);
			verify(clientRepository, times(1)).save(client);

		}
	}
}
