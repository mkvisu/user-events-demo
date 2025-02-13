package edu.labs.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {
	
	@NotNull
    private String userName;
	
	@NotNull
    private String firstName;
	
	@NotNull
    private String lastName;
	
	@Email
	@NotNull
    private String email;
	
	@Size(min = 8)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
    message = "Password must contain at least one digit, one uppercase, one lowercase, and one special character")
    private String password;
	
	@NotNull
    private UserRole role;
}
