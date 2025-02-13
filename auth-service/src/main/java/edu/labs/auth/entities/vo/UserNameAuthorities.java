package edu.labs.auth.entities.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@EqualsAndHashCode(of = {"userName"})
@Data
@NoArgsConstructor
public class UserNameAuthorities {

    private String userName;
    private Set<String> authorities;

}
