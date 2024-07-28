package com.authJWT.authenticationJWT.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.authJWT.authenticationJWT.filter.JwtAuthenticationFilter;
import com.authJWT.authenticationJWT.service.UserDetaileServiceImp;

@Configuration
public class SecurityConfig {
	
	private final UserDetaileServiceImp userDetailsServiceImp;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(UserDetaileServiceImp userDetailsServiceImp,
			JwtAuthenticationFilter jwtAuthenticationFilter) {
		
		this.userDetailsServiceImp = userDetailsServiceImp;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		return httpSecurity 
				.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(
						(req)->req
    					.requestMatchers("/login/**","/register/**").permitAll()
    					.requestMatchers("/api/demo").hasAnyAuthority("USER","ADMIN")
    					.requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
    					.anyRequest()
    					.authenticated()
				)
				.userDetailsService(userDetailsServiceImp)
				.sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
			throws Exception {
		
		return configuration.getAuthenticationManager();
	}
	
}
