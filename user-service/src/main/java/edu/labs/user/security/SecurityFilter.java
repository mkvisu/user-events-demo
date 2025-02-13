package edu.labs.user.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.collect.ImmutableMap;

import edu.labs.user.entities.vo.UserNameAuthorities;
import edu.labs.user.services.AuthService;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static java.util.Optional.ofNullable;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	public static final String TOKEN_PREFIX = "Bearer ";
	
	private final Map<String, List<String>> userRoles = ImmutableMap.of(
	        "ADMIN", new ArrayList<String>(Arrays.asList("ROLE_ADMIN", "ROLE_USER")),
	        "USER", new ArrayList<String>(Arrays.asList("ROLE_USER"))
	    );

	@Autowired
	private AuthService authService;

	
    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(AUTHORIZATION);
        
        if (StringUtils.hasText(token)) {
            String tokenData = token.replace(TOKEN_PREFIX, "");
            Optional<UserNameAuthorities> authorities = authService.validateToken(tokenData);
            Optional<Authentication> authenticaitonObject = authorities.map(this::getFromUsernameAuthorities);
            if(authenticaitonObject.isPresent()) {
            	UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)authenticaitonObject.get();
            	authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            	SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
    
    private UsernamePasswordAuthenticationToken getFromUsernameAuthorities(final UserNameAuthorities usernameAuthorities) {
    	Set<String> roles = usernameAuthorities.getAuthorities();
    	Collection<? extends GrantedAuthority> authorities = null;
    	for(String role: roles) {
    		authorities = userRoles.get(role).stream().map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    	}
        return new UsernamePasswordAuthenticationToken(
                usernameAuthorities.getUserName(),
                null,
                authorities
        );
    
    }

}