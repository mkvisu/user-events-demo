package edu.labs.events.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.labs.events.entities.vo.UserNameAuthorities;


@Service
public class AuthService {

	private final RestTemplate restTemplate;
	
	@Autowired
	public AuthService(RestTemplate restTempalte) {
		this.restTemplate = restTempalte;
	}
	
	public Optional<UserNameAuthorities> validateToken(String token) {
		UserNameAuthorities authorities = restTemplate.getForObject("http://auth-service/auth/validateToken?token=" + token, UserNameAuthorities.class);

		return Optional.ofNullable(authorities);
	}
}
