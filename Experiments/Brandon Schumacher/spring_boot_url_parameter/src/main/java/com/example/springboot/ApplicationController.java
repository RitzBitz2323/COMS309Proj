package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

	@GetMapping("/")
	public String mainMapping(@RequestParam(value="text", defaultValue="Use the 'text' parameter.") String text) {
		return text;
	}
	
}
