package edu.labs.events.entities.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Class used to receive the authorization information related with logged users
 */
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
