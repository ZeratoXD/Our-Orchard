package com.generation.OurOrchard.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class BasicSecurityConfig {

	@Autowired
	private JwtAuthFilter authFilter;

	// Configuração do serviço que fornece detalhes do usuário para autenticação
	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	// Configuração do encoder de senhas utilizando BCrypt
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Configuração do provedor de autenticação DAO
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	// Configuração do gerenciador de autenticação
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	// Configuração da cadeia de filtros de segurança HTTP
	 @Bean
	    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	    	http
		        .sessionManagement(management -> management
		                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		        		.csrf(csrf -> csrf.disable())
		        		.cors(withDefaults());

	    	http
		        .authorizeHttpRequests((auth) -> auth
		                .requestMatchers("/users/login").permitAll()
		                .requestMatchers("/users/register").permitAll()
		                .requestMatchers("/produtos").permitAll()
		                .requestMatchers("/error/**").permitAll()
		                .requestMatchers(HttpMethod.OPTIONS).permitAll()
		                .anyRequest().authenticated())
		        .authenticationProvider(authenticationProvider())
		        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
		        .httpBasic(withDefaults());

			return http.build();

	    }


}
