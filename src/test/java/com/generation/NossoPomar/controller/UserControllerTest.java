package com.generation.NossoPomar.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.NossoPomar.model.User;
import com.generation.NossoPomar.repository.UserRepository;
import com.generation.NossoPomar.service.UserService;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@BeforeAll
	void start(){

		userRepository.deleteAll();

		userService.cadastrarUsuario(new User(1L, "John Doe", "password123", "administrator", "john.doe@example.com", "photo.jpg"));

	}

	@Test
	@DisplayName("Cadastrar Um Usuário")
	public void deveCriarUmUsuario() {

		HttpEntity<User> bodyRequest = new HttpEntity<User>(new User(1L, 
			"Paulo Antunes", "matchupitchu123","administrator","paulo_antunes@test.com","photoXD.jpg"));

		ResponseEntity<User> corpoResposta = testRestTemplate
			.exchange("/usuarios/cadastrar", HttpMethod.POST, bodyRequest, User.class);

		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
	
	}

	@Test
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {

		userService.cadastrarUsuario(new User(1L, "John Doe", "password123", "administrator", "john.doe@example.com", "photo.jpg"));

		HttpEntity<User> bodyRequest = new HttpEntity<User>(new User(1L, "John Doe", "password123", "administrator", "john.doe@example.com", "photo.jpg"));

		ResponseEntity<User> bodyResponse = testRestTemplate
			.exchange("/usuarios/cadastrar", HttpMethod.POST, bodyRequest, User.class);

		assertEquals(HttpStatus.BAD_REQUEST, bodyResponse.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar um Usuário")
	public void deveAtualizarUmUsuario() {

		Optional<User> userRegister = userService.cadastrarUsuario(new User(1L, "John Doe", "password123", "admin", "john.doe@example.com", "photo.jpg"));
		User userUpdate = (new User(1L, "John Doe", "password123", "administrator", "john.doe@example.com", "photo.jpg"));
		
		HttpEntity<User> bodyRequest = new HttpEntity<User>(userUpdate);

		ResponseEntity<User> bodyResponse = testRestTemplate
			.withBasicAuth("root@root.com", "rootroot")
			.exchange("/usuarios/atualizar", HttpMethod.PUT, bodyRequest, User.class);

		assertEquals(HttpStatus.OK, ((ResponseEntity<User>) bodyRequest).getStatusCode());
		
	}

	@Test
	@DisplayName("Listar todos os Usuários")
	public void deveMostrarTodosUsuarios() {

		userService.cadastrarUsuario(new User());
		
		userService.cadastrarUsuario(new User());

		ResponseEntity<String> response = testRestTemplate
		.withBasicAuth("root@root.com", "rootroot")
			.exchange("/usuarios/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

}