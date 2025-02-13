package edu.labs.user.util;

import org.mapstruct.Mapper;

import edu.labs.user.dto.UserDTO;
import edu.labs.user.entities.User;


@Mapper(componentModel = "spring")
public interface UserMapper  {
	
	User userDTOtoUser(UserDTO userDTO);
	UserDTO userToUserDTO(User user);
	
}
