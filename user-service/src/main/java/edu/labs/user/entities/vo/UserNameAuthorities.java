package edu.labs.user.entities.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@EqualsAndHashCode(of = {"username"})
@Data
@NoArgsConstructor
@Setter 
@Getter
public class UserNameAuthorities {

    private String username;
    private Set<String> authorities;

}
