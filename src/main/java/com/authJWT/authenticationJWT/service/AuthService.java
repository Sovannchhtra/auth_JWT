package com.authJWT.authenticationJWT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authJWT.authenticationJWT.entity.AuthResponse;
import com.authJWT.authenticationJWT.entity.User;
import com.authJWT.authenticationJWT.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	

	public AuthResponse register(User req) {
		User user = new User();
		user.setUsername(req.getUsername());
		user.setEmail(req.getEmail());
		user.setPassword(passwordEncoder.encode(req.getPassword()));
		
		user.setRole(req.getRole());
		
		user = userRepository.save(user);
		
		String token = jwtService.generateToken(user);
		
		return new AuthResponse(token);
	}
	
	public AuthResponse authenticate(User req) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						req.getUsername(),
						req.getPassword()
				)
		);
		
		User user = userRepository.findByUsername(req.getUsername()).orElseThrow();
		String token = jwtService.generateToken(user);
		
		return new AuthResponse(token);
		
	}
	
	
	
}
