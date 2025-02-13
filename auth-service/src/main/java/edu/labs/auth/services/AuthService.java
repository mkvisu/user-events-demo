package edu.labs.auth.services;

import edu.labs.auth.entities.AuthRequest;
import edu.labs.auth.entities.AuthResponse;
import edu.labs.auth.entities.User;
import edu.labs.auth.entities.vo.UserNameAuthorities;
import edu.labs.auth.entities.vo.UserVO;
import io.jsonwebtoken.Claims;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(RestTemplate restTemplate,
                       final JwtUtil jwtUtil) {
        this.restTemplate = restTemplate;
        this.jwtUtil = jwtUtil;
    }

    public void register(User userRequest) {
    	userRequest.setSalt(BCrypt.gensalt());
    	userRequest.setPassword(BCrypt.hashpw(userRequest.getPassword(), userRequest.getSalt()));

        try {
        	UserVO userVO = restTemplate.postForObject("http://user-service/users/register", userRequest, UserVO.class);
        	Assert.notNull(userVO, "Failed to register user. Please try again later");
        }catch(RuntimeException exception) {
        	throw exception;
        }       
    }
    
    public AuthResponse login(AuthRequest authRequest) {
        //do validation if user already exists
    	AuthResponse authResponse=null;

        //UserVO userVO = restTemplate.postForObject("http://user-service/users", authRequest, UserVO.class);
        String requestParam = "?userName="+authRequest.getUserName();
     
        UserVO userVO = restTemplate.getForObject("http://user-service/users/login"+ requestParam, UserVO.class);
        
        String password = BCrypt.hashpw(authRequest.getPassword(), userVO.getSalt());
        if(StringUtils.equals(password, userVO.getPassword())) {
        	String accessToken = jwtUtil.generate(userVO, "ACCESS");
        	String refreshToken = jwtUtil.generate(userVO, "REFRESH");
        	authResponse = new AuthResponse(accessToken, refreshToken);
        }
        return authResponse;

    }
    
    public UserNameAuthorities validateToken(String token) {
    	UserNameAuthorities auth = new UserNameAuthorities();
    	if(jwtUtil.validateToken(token)) {
    		Claims claims = jwtUtil.getAllClaimsFromToken(token);
    		auth.setUserName(String.valueOf(claims.get("userName")));
    		auth.setAuthorities(new HashSet<String>(Arrays.asList(String.valueOf(claims.get("role")))));
    		return auth;
    	}
    	return auth;
    }
}
