package edu.labs.auth.entities.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@EqualsAndHashCode(of = {"username"})
@Data
@NoArgsConstructor
public class UserNameAuthorities {

    @JsonProperty("user_name")
    private String username;
    private Set<String> authorities;

}
