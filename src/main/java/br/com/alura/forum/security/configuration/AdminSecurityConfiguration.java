package br.com.alura.forum.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.alura.forum.security.service.UsersService;

@Configuration
@Order(1)
public class AdminSecurityConfiguration {

	@Autowired
	private UsersService usersServices;
	
	protected void configure(HttpSecurity http) throws Exception{
		http.antMatcher("/admin/**")
			.authorizeRequests().anyRequest().hasRole("ADMIN")
		.and()
			.httpBasic();
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(this.usersServices)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
