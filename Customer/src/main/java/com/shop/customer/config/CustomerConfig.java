package com.shop.customer.config;

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
public class CustomerConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomerServiceConfig();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }


    
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     http.authenticationProvider(daoAuthenticationProvider())
     		.authorizeHttpRequests()
     		.antMatchers("/**").permitAll()
     		.antMatchers("/customer/**").hasRole("CUSTOMER")
     		.and()
     		.formLogin()
     		.loginPage("/login")
     		.loginProcessingUrl("/do-login")
     		.defaultSuccessUrl("/home")
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
