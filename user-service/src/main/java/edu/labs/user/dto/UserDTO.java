package edu.labs.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
	@NotEmpty
    private String userName;
	
	@NotNull
	@NotEmpty
    private String firstName;
	
	@NotNull
	@NotEmpty
    private String lastName;
	
	@Email
	@NotNull
	@NotEmpty
    private String email;
	
	@Size(min = 8)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
    message = "Password must contain at least one digit, one uppercase, one lowercase, and one special character")
	@NotEmpty
	@JsonProperty(access = Access.WRITE_ONLY)
    private String password;
	
	@NotNull
    private UserRole role;
}
