package coms309.roundTrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.roundTrip.model.Trivia;
import coms309.roundTrip.repository.TriviaRepository;

@RestController
public class TriviaController {
	
	
	
	@Autowired
	TriviaRepository triviaRepository;
	
	@GetMapping("trivia/all")
	List<Trivia> GetAllTrivia(){
		return triviaRepository.findAll();
	}
	
	@PostMapping("trivia/post/{q}/{a}")
	Trivia PostTriviaByPath(@PathVariable String q, @PathVariable String a) {
		Trivia newTrivia = new Trivia();
		newTrivia.setAnswer(a);
		newTrivia.setQuestion(q);
		triviaRepository.save(newTrivia);
		return newTrivia;
	}
	
	@PostMapping("trivia/post")
	Trivia PostTriviaByPath(@RequestBody Trivia newTrivia) {
		triviaRepository.save(newTrivia);
		return newTrivia;
	}
}
