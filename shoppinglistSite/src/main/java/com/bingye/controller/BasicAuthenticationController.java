package com.bingye.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bingye.security.AuthenticationBean;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class BasicAuthenticationController {
	
	@GetMapping(path = "/basicauth")
	public AuthenticationBean helloWorldBean() {
		return new AuthenticationBean("You are authenticated");
	}
}
