package edu.labs.auth.controllers;

import edu.labs.auth.entities.AuthRequest;
import edu.labs.auth.entities.AuthResponse;
import edu.labs.auth.entities.User;
import edu.labs.auth.entities.vo.UserNameAuthorities;
import edu.labs.auth.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody User userRequest) {
    	try {
    		authService.register(userRequest);
    	}catch(RuntimeException exception) {
    		ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    	}
        
        return ResponseEntity.ok("Success");
    }
    
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authrequest){
    	AuthResponse  authResponse = null;
    	if(authrequest != null) {
    		authResponse = authService.login(authrequest);
    		if(authResponse == null)
    			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    	}
    	return ResponseEntity.badRequest().body(authResponse);
    }
    
    @GetMapping(value = "/validateToken")
    public ResponseEntity<UserNameAuthorities> validateToken(@RequestParam String token){
    	UserNameAuthorities auth = authService.validateToken(token);
    	
    	return ResponseEntity.ok(auth);
    }

}
