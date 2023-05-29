package com.shop.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
public class AdminConfig {
	@Bean
	public UserDetailsService userDetailsService() {
		return new AdminServiceConfig();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
	    DaoAuthenticationProvider provider =
	            new DaoAuthenticationProvider();
	    // using bCryptPasswordEncoder()
	    provider.setPasswordEncoder(passwordEncoder());
	    provider.setUserDetailsService(userDetailsService());
	    return provider;
	}
	

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     http.authenticationProvider(daoAuthenticationProvider())
     		.authorizeHttpRequests()
     		.antMatchers("/**").permitAll()
     		.antMatchers("/admin/**").hasRole("ADMIN")
     		.and()
     		.formLogin()
     		.loginPage("/login")
     		.loginProcessingUrl("/do-login")
     		.defaultSuccessUrl("/index")
//     		.failureForwardUrl("/login?error")
     		.permitAll()
     		.and()
     		.logout()
     		.invalidateHttpSession(true)
     		.clearAuthentication(true)
     		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
     		.logoutSuccessUrl("/login?logout")
     		.permitAll()
     		;
         return http.build();
	}
	
}








































