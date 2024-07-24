package com.generation.NossoPomar.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.NossoPomar.model.User;
import com.generation.NossoPomar.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Carrega o usuário pelo email (nome de usuário)
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        // Busca o usuário no repositório pelo email
        Optional<User> user = userRepository.findByEmail(userEmail);

        // Se o usuário for encontrado, retorna uma nova instância de UserDetailsImpl
        if (user.isPresent())
            return new UserDetailsImpl(user.get());
        else
            // Se o usuário não for encontrado, lança uma exceção com status FORBIDDEN
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
