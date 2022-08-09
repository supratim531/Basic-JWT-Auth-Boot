package com.security.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.app.model.JwtRequest;
import com.security.app.model.JwtResponse;
import com.security.app.service.MyUserDetailsService;
import com.security.app.util.JwtUtil;

@RestController
public class JwtController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) {
		String username = jwtRequest.getUsername();
		String password = jwtRequest.getPassword();

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			System.out.println("----- " + e.getMessage() + " -----");
			return ResponseEntity.status(401).body("<h1>Bad Credential</h1>");
		}

		UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
		String jwt = jwtUtil.generateToken(userDetails);
		System.out.println("----- JWT: " + jwt + " -----");
		return ResponseEntity.status(201).body(new JwtResponse(jwt));
	}

}
