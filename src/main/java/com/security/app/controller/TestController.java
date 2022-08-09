package com.security.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/")
	public String home() {
		String message = "<h1>This is home page (Public)</h1>";
		return message;
	}

	@GetMapping("/welcome")
	public String welcome() {
		String message = "<h1>Welcome user to our website (Private)</h1>";
		return message;
	}

}
