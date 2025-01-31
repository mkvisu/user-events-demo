package edu.labs.user.services;

import edu.labs.user.entities.User;
import edu.labs.user.entities.vo.ResponseTemplateVO;
import edu.labs.user.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final RestTemplate restTemplate;

    @Autowired
    public UserService(UserRepository repository,
                       RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }


    public User save(User user) {
        return this.repository.saveAndFlush(user);
    }

    public Optional<User> login(String userName, String password) {
    	User user = this.getByUserName(userName);
    	
    	if(StringUtils.equals(password, user.getPassword())) {
    		return Optional.ofNullable(user);
    	}
    	
    	return Optional.ofNullable(null);
    }
    
    public User getByUserName(String userName) {
        return this.repository.findByUserName(userName).orElse(null);
    }

    public ResponseTemplateVO getUserWithEvent(String userName) {
        User user = this.getByUserName(userName);
        user.setPassword(null);
        user.setSalt(null);

        //Department department = restTemplate.getForObject("http://events-service/events/" + user.getDepartmentId(), Department.class);
        return new ResponseTemplateVO(user, null);
       // return new ResponseTemplateVO(user, department);
    }
    
    public void deleteUser(String userName) {
    	repository.deleteById(userName);
    }
}
