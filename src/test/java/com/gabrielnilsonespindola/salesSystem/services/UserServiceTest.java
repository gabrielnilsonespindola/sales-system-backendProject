package com.gabrielnilsonespindola.salesSystem.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import com.gabrielnilsonespindola.salesSystem.dto.UserDTO;
import com.gabrielnilsonespindola.salesSystem.entities.Role;
import com.gabrielnilsonespindola.salesSystem.entities.Role.Values;
import com.gabrielnilsonespindola.salesSystem.entities.User;
import com.gabrielnilsonespindola.salesSystem.repositories.RoleRepository;
import com.gabrielnilsonespindola.salesSystem.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	UserRepository userRepository;

	@Mock
	RoleRepository roleRepository;

	@InjectMocks
	UserService userService;

	@Captor
	private ArgumentCaptor<User> userCaptor;

	@Nested
	class findAll {

		@Test
		void findAllTestUsersOk() {

			// Arrange
			User user1 = new User(1L, "jao", "jao@gmail.com", "123", "jao123");
			User user2 = new User(2L, "dom", "dom@gmail.com", "123", "dom123");

			List<User> listAll = List.of(user1, user2);
			when(userRepository.findAll()).thenReturn(listAll);

			// Action
			List<User> listAllFull = userService.findAll();

			// Assert
			assertNotNull(listAllFull);
			assertEquals(2, listAllFull.size());
			assertEquals("jao", listAllFull.get(0).getName());
			assertEquals("dom", listAllFull.get(1).getName());
			verify(userRepository, times(1)).findAll();

		}
	}

	@Nested
	class newUser {

		@Test
		void checkIfTheUserIsNotPresentAllowingTheRegistrationOfANewOne() {

			// ARRANGE

			UserDTO dto = new UserDTO();
			dto.setId(1L);
			dto.setName("jao");
			dto.setEmail("jao@gmail.com");
			dto.setUsername("jao123");
			dto.setPassword("123");

			Role role1 = new Role();
			role1.setName(Values.basic.name());
			role1.setRoleid(2L);

			User user1 = new User();
			user1.setId(dto.getId());
			user1.setName(dto.getName());
			user1.setEmail(dto.getEmail());
			user1.setUsername(dto.getUsername());
			user1.setPassword(dto.getPassword());
			user1.setRoles(Set.of(role1));

			when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.empty());
			when(roleRepository.findByName("basic")).thenReturn(role1);

			// ACTION + ASSERT

			assertDoesNotThrow(() -> userService.newUser(dto));

			verify(userRepository, times(1)).findByUsername(user1.getUsername());
			verify(roleRepository, times(1)).findByName(role1.getName());

		}

		@Test
		void checkIfTheUserExistsAndIfItIsThrowingTheResponseException() {

			// ARRANGE

			UserDTO dto = new UserDTO();
			dto.setId(1L);
			dto.setName("jao");
			dto.setEmail("jao@gmail.com");
			dto.setUsername("jao123");
			dto.setPassword("123");

			Role role1 = new Role();
			role1.setName(Values.basic.name());
			role1.setRoleid(2L);

			User user1 = new User();
			user1.setId(dto.getId());
			user1.setName(dto.getName());
			user1.setEmail(dto.getEmail());
			user1.setUsername(dto.getUsername());
			user1.setPassword(dto.getPassword());
			user1.setRoles(Set.of(role1));

			when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));
			when(roleRepository.findByName("basic")).thenReturn(role1);

			// ACTION + ASSERT

			ResponseStatusException exceptionResponse = assertThrows(ResponseStatusException.class,
					() -> userService.newUser(dto));
			assertEquals(exceptionResponse.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);

		}

		@Test
		void shouldCallRepositorySave() {

			// ARRANGE

			UserDTO dto = new UserDTO();
			dto.setId(1L);
			dto.setName("jao");
			dto.setEmail("jao@gmail.com");
			dto.setUsername("jao123");
			dto.setPassword("123");

			Role role1 = new Role();
			role1.setName(Values.basic.name());
			role1.setRoleid(2L);

			User user1 = new User();
			user1.setId(dto.getId());
			user1.setName(dto.getName());
			user1.setEmail(dto.getEmail());
			user1.setUsername(dto.getUsername());
			user1.setPassword(dto.getPassword());
			user1.setRoles(Set.of(role1));

			when(userRepository.save(any(User.class))).thenReturn(user1);
			when(roleRepository.findByName("basic")).thenReturn(role1);

			// ACTION

			User userNew = userService.newUser(dto);

			// ASSERT

			assertNotNull(userNew);
			verify(userRepository).save(userCaptor.capture());
			User capturedUser = userCaptor.getValue();
			assertTrue(capturedUser.getRoles().contains(role1));
			assertEquals("jao123", capturedUser.getUsername());
			verify(userRepository, times(1)).save(capturedUser);

		}

	}
}
