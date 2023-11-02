package com.hackernews.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class SecurityConfig {
	
	@Bean
	public UserDetailsService getUserDetailService() {
		return new UserDetailServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		daoAuthenticationProvider.setUserDetailsService(getUserDetailService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChani(HttpSecurity http) throws Exception {
		http
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/submit","/hide","/upvote","/upvotecomment","/comment","/userpage").authenticated() 
                .anyRequest().permitAll())
            	.formLogin(login ->login.loginPage("/login")
                		.loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
                		)
                .logout(logout -> 
                		logout.permitAll());
 
    return http.build();
	}

}
