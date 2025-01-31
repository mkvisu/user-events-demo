package edu.labs.user.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter{

    private final String SPRING_ACTUATOR_PATH = "/actuator";
    private final String ALLOW_ALL_ENDPOINTS1 = "/**";
    private final String ALLOW_ALL_ENDPOINTS2 = "/register";

    //private final DocumentationConfiguration documentationConfiguration;

    private final SecurityFilter securityFilter;

    @Autowired
    private PasswordEncoder encoder;
    
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    public WebSecurity(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter; 
    }
    
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(encoder.encoder());
//    }
    
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
//    	 http.cors().and().csrf().disable()
//         .authorizeRequests()
//         .antMatchers("/users/register","/users/login").permitAll()
//         .anyRequest().authenticated()
//         .and()
//         .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
//         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//    	 http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        http
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            // Stateless session management
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // Handle unauthorized access
            .exceptionHandling()
            .authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
            .authorizeRequests()
            // Allow preflight requests
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers(HttpMethod.GET, allowedGetEndpoints()).permitAll()
            .antMatchers("/users/register","/users/login").permitAll()
            // All other requests must be authenticated
            .anyRequest().authenticated()
            .and()
            // Add custom security filter
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private String[] allowedGetEndpoints() {
        return new String[] {
                SPRING_ACTUATOR_PATH + ALLOW_ALL_ENDPOINTS1
        };
    }

}