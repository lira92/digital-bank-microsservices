package br.com.biopark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http
				.httpBasic(basic -> basic.disable())
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						authorizeHttpRequests -> authorizeHttpRequests
						.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/api/**").permitAll()
						.requestMatchers("/users").denyAll()
				)
				.cors(cors -> {})
				.build();
	}
}
