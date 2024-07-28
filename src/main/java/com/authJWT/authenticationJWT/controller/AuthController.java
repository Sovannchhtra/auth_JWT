package com.authJWT.authenticationJWT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authJWT.authenticationJWT.entity.AuthResponse;
import com.authJWT.authenticationJWT.entity.User;
import com.authJWT.authenticationJWT.service.AuthService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody User req){
		return ResponseEntity.ok(authService.register(req));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody User req){
		return ResponseEntity.ok(authService.authenticate(req));
	}
}
