package com.generation.OurOrchard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.OurOrchard.model.User;



public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByEmail(String email);

}