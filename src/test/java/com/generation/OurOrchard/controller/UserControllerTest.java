package com.generation.OurOrchard.controller;

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

import com.generation.OurOrchard.model.User;
import com.generation.OurOrchard.repository.UserRepository;
import com.generation.OurOrchard.service.UserService;



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

		userService.userRegister(new User(1L, "Teste", "rootroot", "Admin", "root@rootxd.com", "--"));

	}

	@Test
	@DisplayName("Cadastrar Um Usuário")
	public void deveCriarUmUsuario() {

		HttpEntity<User> bodyRequest = new HttpEntity<User>(new User(1L, "Teste", "rootroot", "Admin", "root@rootxd.com", "--"));

		ResponseEntity<User> bodyResponse = testRestTemplate
			.exchange("/user/register", HttpMethod.POST, bodyRequest, User.class);

		assertEquals(HttpStatus.CREATED, bodyResponse.getStatusCode());
	
	}

	@Test
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {

		userService.userRegister(new User(1L, "Teste", "rootroot", "Admin", "root@rootxd.com", "--"));

		HttpEntity<User> bodyRequest = new HttpEntity<User>(new User(1L, "Teste", "rootroot", "Admin", "root@rootxd.com", "--"));

		ResponseEntity<User> bodyResponse = testRestTemplate
			.exchange("/user/register", HttpMethod.POST, bodyRequest, User.class);

		assertEquals(HttpStatus.BAD_REQUEST, bodyResponse.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar um Usuário")
	public void deveAtualizarUmUsuario() {

		Optional<User> userRegister = userService.userRegister(new User( 1L, "Teste", "rootroot", "Admin", "root@rootxd.com", "--"));
		User userUpdate = (new User(1L, "Teste", "rootroot", "Admin", "root@rootxd.com", "foto.jpg"));
		
		HttpEntity<User> bodyRequest = new HttpEntity<User>(userUpdate);

		ResponseEntity<User> bodyResponse = testRestTemplate
			.withBasicAuth("root@rootxd.com", "rootroot")
			.exchange("/user/register", HttpMethod.PUT, bodyRequest, User.class);

		assertEquals(HttpStatus.OK, ((ResponseEntity<User>) bodyRequest).getStatusCode());
		
	}

	@Test
	@DisplayName("Listar todos os Usuários")
	public void deveMostrarTodosUsuarios() {

		userService.userRegister(new User());
		
		userService.userRegister(new User());

		ResponseEntity<String> response = testRestTemplate
		.withBasicAuth("root@rootxd.com", "rootroot")
			.exchange("/user/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

}

