package coms309.roundTrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coms309.roundTrip.model.Actor;
import coms309.roundTrip.repository.ActorRepository;

@RestController
public class ActorController {

	@Autowired
	ActorRepository actorRepository;
	
	@GetMapping("/actors")
	public List<Actor> getActors() {
		return actorRepository.findAll();
	}
	
	@GetMapping("/actors/{id}")
	Actor findActorById(@PathVariable int id) {
		return actorRepository.findById(id);
	}
	
	@PostMapping("/actors/add")
	public Actor add(@RequestBody Actor actor) {
		
		if(actor == null) return null;
		
		actorRepository.save(actor);
		return actor;
	}
	
	@PostMapping("/actors/add/{first_name}/{last_name}")
	public Actor addActor(@PathVariable String first_name, 
			@PathVariable String last_name, 
			@RequestParam(value="passworld", defaultValue="password") String password, 
			@RequestParam(value="type") Integer user_type, 
			@RequestBody Integer[] ticketIDs, 
			@RequestParam(value="num_of_tickets", defaultValue="0") Integer numOfTickets,
			@RequestParam(value="rating", defaultValue="0.0") Float rating) {
		
		Actor newActor = new Actor();
		newActor.setFirstName(first_name);
		newActor.setLastName(last_name);
		newActor.setUserType(user_type);
		newActor.setPassword(password.hashCode());
		newActor.setTicketIds(ticketIDs);
		newActor.setNumOfTickets(numOfTickets);
		newActor.setRating(rating);
		actorRepository.save(newActor);
		return newActor;
	}
	
	
}
