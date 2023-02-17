package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@ComponentScan(basePackages = { "com.smart.config" })
@EnableWebSecurity
public class MyConfig {

	//bean used to create object used in other locations (configuration object)
	@Bean    
	public UserDetailsService getUserDetailsService() {  
		return new UserDetailServiceImpl();	
	}
			
	//configuring methods
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.getUserDetailsService());
		return provider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.authorizeRequests(auth -> {
					auth.antMatchers("/admin/").hasRole("ADMIN");
					auth.antMatchers("/user/").hasRole("USER");
					auth.antMatchers("/").permitAll();
				})
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
				.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(Customizer.withDefaults())
				.build();
		
		
//		
//		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
//		.antMatchers("/user/**").hasRole("USER")
//		.antMatchers("/**").permitAll().and().formLogin().loginPage("/login").usernameParameter("email").passwordParameter("password")
//		.successForwardUrl("/")
//		.and().csrf().disable();
//		http.authenticationProvider(daoAuthenticationProvider());
//		DefaultSecurityFilterChain defaultSecurityFilterChain =  http.build();
//		
//		return defaultSecurityFilterChain;
	}

	
	
}
