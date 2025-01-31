package edu.labs.auth.entities.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserVO {


	private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String salt;

}
