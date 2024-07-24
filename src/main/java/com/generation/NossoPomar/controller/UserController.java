package com.generation.NossoPomar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.NossoPomar.model.User;
import com.generation.NossoPomar.model.UserLogin;
import com.generation.NossoPomar.repository.UserRepository;
import com.generation.NossoPomar.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAll() {

		return ResponseEntity.ok(userRepository.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {
		return userRepository.findById(id).map(response -> ResponseEntity.ok(response))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/login")
	public ResponseEntity<UserLogin> authenticateUser(@RequestBody Optional<UserLogin> userLogin) {

		return userService.autenticarUsuario(userLogin)
				.map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/register")
	public ResponseEntity<User> postUser(@RequestBody @Valid User user) {
  System.out.println("User: " + user.toString());
		return userService.cadastrarUsuario(user)
				.map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

	}

	@PutMapping("/update")
	public ResponseEntity<User> putUser(@Valid @RequestBody User user) {

		return userService.atualizarUsuario(user)
				.map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

}
