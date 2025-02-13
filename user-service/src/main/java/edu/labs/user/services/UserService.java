package edu.labs.user.services;


import edu.labs.user.dto.UserDTO;
import edu.labs.user.dto.UserUpdateDTO;
import edu.labs.user.entities.User;
import edu.labs.user.exceptions.UserNotFoundException;
import edu.labs.user.repos.UserRepository;
import edu.labs.user.util.UserMapper;
import edu.labs.user.util.UserUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Optional;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final RestTemplate restTemplate;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository repository,
                       RestTemplate restTemplate, UserMapper userMapper) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.userMapper = userMapper;
    }


    @Transactional
    public UserDTO save(UserDTO user) {
    	User mappedUser = userMapper.userDTOtoUser(user);
    	mappedUser.setSalt(BCrypt.gensalt());
    	mappedUser.setPassword(BCrypt.hashpw(mappedUser.getPassword(), mappedUser.getSalt()));
    	User savedUser = this.repository.saveAndFlush(mappedUser);
    	UserDTO userDto = userMapper.userToUserDTO(savedUser);
    	return userDto;
    }

    public Optional<UserDTO> login(String userName, String password) {
    	User user = this.repository.findByUserName(userName).orElse(null);
        if(user == null) {
        	throw new UserNotFoundException(userName);
        }
    	if(StringUtils.equals(password, user.getPassword())) {
    		UserDTO userDto = userMapper.userToUserDTO(user);
    		return Optional.ofNullable(userDto);
    	}
    	return Optional.ofNullable(null);
    }
    
    public UserDTO getByUserName(String userName) throws UserNotFoundException {
        User user = this.repository.findByUserName(userName).orElse(null);
        if(user == null) {
        	throw new UserNotFoundException(userName);
        }
        UserDTO userDto = userMapper.userToUserDTO(user);
        return userDto;
    }

    public User getUser(String userName) {
    	User user = this.repository.findByUserName(userName).orElse(null);
    	
    	if(user == null) {
    		throw new UserNotFoundException(userName);
    	}
    	//UserDTO userDto = userMapper.userToUserDTO(user);
    	return user;
   }
    
    @Transactional
    public void updateUser(UserUpdateDTO userUpdateFrom, String userName) {

    	User savedUser = this.repository.findByUserName(userName)
    					.map(user ->{
    						if(UserUtils.isNotBlank(userUpdateFrom.getFirstName()))
    							user.setFirstName(userUpdateFrom.getFirstName());
    						if(UserUtils.isNotBlank(userUpdateFrom.getLastName()))
    							user.setLastName(userUpdateFrom.getLastName());
    						if(UserUtils.isNotBlank(userUpdateFrom.getPassword()))
    							UserUtils.encryptPassword(user, userUpdateFrom.getPassword());
    						if(UserUtils.isNotBlank(userUpdateFrom.getEmail()))
    							user.setEmail(userUpdateFrom.getEmail());
    						if(userUpdateFrom.getRole() != null)
    							user.setRole(userUpdateFrom.getRole().name());
    						return this.repository.saveAndFlush(user);
    					}).orElse(null);
    	
    	if(savedUser == null) {
    		throw new UserNotFoundException(userName);
    	}
    }
    
    public void deleteUser(String userName) {
    	repository.deleteById(userName);
    }
}
