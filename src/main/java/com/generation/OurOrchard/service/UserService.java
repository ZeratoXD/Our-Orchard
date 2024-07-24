package com.generation.OurOrchard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.OurOrchard.model.User;
import com.generation.OurOrchard.model.UserLogin;
import com.generation.OurOrchard.repository.UserRepository;
import com.generation.OurOrchard.security.JwtService;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public Optional<User> userRegister(User user) {

		if (userRepository.findByEmail(user.getEmail()).isPresent())
			return Optional.empty();

		user.setPassword(encryptPassword(user.getPassword()));

		return Optional.of(userRepository.save(user));

	}

	public Optional<User> userUpdate(User user) {

		if (userRepository.findById(user.getId()).isPresent()) {

			Optional<User> userSearch = userRepository.findByEmail(user.getEmail());

			if ((userSearch.isPresent()) && (userSearch.get().getId() != user.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists!", null);

			user.setPassword(encryptPassword(user.getPassword()));

			return Optional.ofNullable(userRepository.save(user));

		}

		return Optional.empty();

	}

	public Optional<UserLogin> userAuthenticate(Optional<UserLogin> userLogin) {

		var credentials = new UsernamePasswordAuthenticationToken(userLogin.get().getEmail(),
				userLogin.get().getPassword());

		Authentication authentication = authenticationManager.authenticate(credentials);

		if (authentication.isAuthenticated()) {

			Optional<User> user = userRepository.findByEmail(userLogin.get().getEmail());

			if (user.isPresent()) {

				userLogin.get().setId(user.get().getId());
				userLogin.get().setName(user.get().getName());
				userLogin.get().setEmail(user.get().getEmail());
				userLogin.get().setPhoto(user.get().getPhoto());
				userLogin.get().setType(user.get().getType());
				userLogin.get().setToken(gerarToken(userLogin.get().getEmail()));
				userLogin.get().setPassword("");

				return userLogin;

			}

		}

		return Optional.empty();

	}

	private String encryptPassword(String password) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(password);

	}

	private String gerarToken(String user) {
		return "Bearer " + jwtService.generateToken(user);
	}

}