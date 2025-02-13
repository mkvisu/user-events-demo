package edu.labs.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

import edu.labs.user.audit.SpringSecurityAuditorAware;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	 @Bean
	 public AuditorAware<String> auditorProvider() {
	    return new SpringSecurityAuditorAware();
	 }

}
