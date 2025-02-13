package edu.labs.user.controllers;

import edu.labs.user.dto.UserDTO;
import edu.labs.user.dto.UserUpdateDTO;
import edu.labs.user.entities.User;
import edu.labs.user.entities.vo.AuthRequest;
import edu.labs.user.exceptions.UserNotFoundException;
import edu.labs.user.services.UserService;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> save(@RequestBody @Valid UserDTO user) {
        UserDTO userDto = userService.save(user);
        
        HttpHeaders responseHeaders = new HttpHeaders();
    	URI newPollUri = ServletUriComponentsBuilder
					    	.fromCurrentRequest()
					    	.path("/{id}")
					    	.buildAndExpand(userDto.getUserName())
					    	.toUri();
    	responseHeaders.setLocation(newPollUri);
    	return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
    
    @GetMapping("/login")
    public User login(@RequestParam String userName) {
    	User user = null;
    	try {
    		user = userService.getUser(userName);
    	}catch(RuntimeException exception) {
    		ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    	}
        return user;
    }

    @GetMapping("/{userName}")
    public UserDTO getUser(@PathVariable String userName) {
    
    	UserDTO userDto = userService.getByUserName(userName);
    	userDto.setPassword(null);
    	return userDto;
    }
    
    @PutMapping("/{userName}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO user, @PathVariable String userName) {
    	try {
    		userService.updateUser(user, userName);
    	}catch(UserNotFoundException notFoundException) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getMessage());
    	}catch(RuntimeException exception) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    	}
    	return ResponseEntity.status(HttpStatus.ACCEPTED).body("User Updated");
    }

    @DeleteMapping("/{userName}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String userName){
    	try {
    		userService.deleteUser(userName);
    	}catch(RuntimeException exception) {
    		ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    	}
        
        return ResponseEntity.ok("Success");
    }
}
