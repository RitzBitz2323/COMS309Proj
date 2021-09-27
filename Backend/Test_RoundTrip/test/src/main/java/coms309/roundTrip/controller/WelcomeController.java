package coms309.roundTrip.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	 @GetMapping("/")
	 public String index() {
	  return "Greetings from Spring Boot! Welcome to ComS309!!";
	 }

	}

