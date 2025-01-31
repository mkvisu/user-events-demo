package edu.labs.user.controllers;

import edu.labs.user.entities.User;
import edu.labs.user.entities.vo.AuthRequest;
import edu.labs.user.entities.vo.ResponseTemplateVO;
import edu.labs.user.services.UserService;

import java.util.Optional;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User save(@RequestBody User user) {
        return userService.save(user);
    }
    
    @GetMapping("/login")
    public User login(@RequestParam String userName) {
    	User user = null;
    	try {
    		user = userService.getByUserName(userName);
    	}catch(RuntimeException exception) {
    		ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    	}
        return user;
    }

    @GetMapping
    public ResponseTemplateVO getUser(String userId, String role) {
        return userService.getUserWithEvent(userId);
    }
    
    @GetMapping("/{userName}")
    public ResponseTemplateVO getUser(@PathVariable String userName) {
        return userService.getUserWithEvent(userName);
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
