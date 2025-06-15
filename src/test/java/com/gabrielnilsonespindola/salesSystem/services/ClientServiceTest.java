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

			// ARRANGE
			Client client1 = new Client(1L, "Jao", "09514530926", "jao@gmail.com");
			Client client2 = new Client(2L, "Tao", "09514530926", "tao@gmail.com");

			List<Client> listAll = List.of(client1, client2);
			when(clientRepository.findAll()).thenReturn(listAll);

			// ACTION
			List<Client> listAllFull = clientService.findAll();

			// ASSERT

			assertNotNull(listAllFull);
			assertEquals(2, listAllFull.size());
			assertEquals("09514530926", listAllFull.get(0).getCpf());
			verify(clientRepository, times(1)).findAll();

		}

	}

	@Nested
	class findById {

		@Test
		void findByIdOk() {

			// ARRANGE
			Client client1 = new Client(1L, "Jao", "09514530926", "jao@gmail.com");
			Client client2 = new Client(2L, "Tao", "09514530926", "tao@gmail.com");

			when(clientRepository.findById(1L)).thenReturn(Optional.of(client1));
			when(clientRepository.findById(2L)).thenReturn(Optional.of(client2));

			// ACTION
			Client listById1 = clientService.findById(1L);
			Client listById2 = clientService.findById(2L);

			// ASSERT

			assertNotNull(listById1);
			assertNotNull(listById2);
			assertEquals(1L, listById1.getId());
			assertEquals(2L, listById2.getId());
			verify(clientRepository, times(1)).findById(1L);
			verify(clientRepository, times(1)).findById(2L);

		}

		@Test
		void findByIdNotOk() {

			// ARRANGE

			when(clientRepository.findById(99L)).thenReturn(Optional.empty());

			// ACTION + ASSERT

			assertThrows(ObjectNotFoundException.class, () -> clientService.findById(99L));
			verify(clientRepository, times(1)).findById(99L);

		}

	}

	@Nested
	class validation {

		@Test
		void validationOk() {

			// ARRANGE

			String cpf = "09514530926";
			when(cpfValidator.invalidMessagesFor(cpf)).thenReturn(Collections.emptyList());

			// ACTION + ASSERT

			assertDoesNotThrow(() -> clientService.validation(cpf));
			verify(cpfValidator, times(1)).invalidMessagesFor(cpf);

		}

		@Test
		void validationNotOk() {

			// ARRANGE

			String cpf = "09514599999";

			when(cpfValidator.invalidMessagesFor(cpf)).thenReturn(List.of(() -> ("CPF INVALIDO")));

			// ACTION + ASSERT

			assertThrows(ObjectNotFoundException.class, () -> clientService.validation(cpf));
			verify(cpfValidator, times(1)).invalidMessagesFor(cpf);

		}
	}

	@Nested
	class registerClient {

		@Test
		void registerClientIfValidationIsOkAndNotHaveCpfRegister() {

			// ARRANGE
			String cpf = "09514530926";
			ClientDTO dto = new ClientDTO();
			dto.setCpf(cpf);

			when(cpfValidator.invalidMessagesFor(cpf)).thenReturn(Collections.emptyList());
			when(clientRepository.findByCpf(cpf)).thenReturn(Optional.empty());

			// ACTION + ASSERT

			assertDoesNotThrow(() -> clientService.registerClient(dto, cpf));
			verify(clientRepository, times(1)).findByCpf(cpf);
			verify(cpfValidator, times(1)).invalidMessagesFor(cpf);

		}

		@Test
		void NotRegisterClientIfValidationNotOk() {

			// ARRANGE

			String cpf = "99999999999";
			ClientDTO dto = new ClientDTO();
			dto.setCpf(cpf);
			when(cpfValidator.invalidMessagesFor(cpf)).thenReturn(List.of(() -> ("CPF INVALIDO")));

			// ACTION + ASSERT

			assertThrows(ObjectNotFoundException.class, () -> clientService.registerClient(dto, cpf));
			verify(cpfValidator, times(1)).invalidMessagesFor(cpf);
		}

		@Test
		void NotRegisterClientIfCpfAlreadyExist() {

			// ARRANGE

			String cpf = "09514530926";
			ClientDTO dto = new ClientDTO();
			dto.setCpf(cpf);

			Client client = new Client();
			client.setCpf(cpf);

			when(clientRepository.findByCpf(cpf)).thenReturn(Optional.of(client));
			when(cpfValidator.invalidMessagesFor(cpf)).thenReturn(List.of(() -> ("CPF INVALIDO")));

			// ACTION + ASSERT

			assertThrows(ObjectNotFoundException.class, () -> clientService.registerClient(dto, cpf));
			verify(clientRepository, times(1)).findByCpf(cpf);
			verify(cpfValidator, times(1)).invalidMessagesFor(cpf);

		}

		@Test
		void registerClientSaveRepositoryOk() {

			// ARRANGE

			String cpf = "09514530926";
			ClientDTO dto = new ClientDTO();
			dto.setName("jao");
			dto.setCpf(cpf);
			dto.setEmail("jao@gmail.com");

			Client client = new Client();
			client.setName(dto.getName());
			client.setCpf(dto.getCpf());
			client.setEmail(dto.getEmail());

			when(clientRepository.save(any(Client.class))).thenReturn(client);

			// ACTION

			Client newClient = clientService.registerClient(dto, cpf);

			// ASSERT

			assertNotNull(newClient);
			verify(clientRepository, times(1)).save(client);

		}
	}

}
