package coms309.cyhelp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Ticket;
import coms309.cyhelp.repository.ActorRepository;

@RestController
public class ActorController {

	@Autowired
	ActorRepository actorRepository;
	
	// GET all actors
	@GetMapping("/actors")
	public List<Actor> getActors() {
		return actorRepository.findAll();
	}
	
	/**
	 * Creates a Actor
	 * @param actor
	 * @return
	 */
	@PostMapping("/actors")
	public Actor addActor(@RequestBody Actor actor) {
		
		if(actor == null) return null;
		
		List<Actor> actors = actorRepository.findAll();
		for(Actor check : actors) {
			System.out.println(check.getUsername());
			if(actor.getUsername().equals(check.getUsername())) return null;
		}
		
		actorRepository.save(actor);
		return actor;
	}
	
	/**
	 * Updates an actor.
	 * @param actor
	 * @return
	 */
	@PutMapping("/actors")
	public Actor updateActor(@RequestBody Actor actor) {
		
		if(actor == null) return null;
		if(actorRepository.findById(actor.getId()) == null) return null;
		
		actorRepository.save(actor);
		return actorRepository.findById(actor.getId());
	}
	
	@PostMapping("/actors/login")
	public Actor actorLogin(@RequestBody Actor actor) {
		
		String username = actor.getUsername();
		Long password = actor.getPassword();
		
		List<Actor> actors = actorRepository.findAll();
		
		for(Actor check : actors) {
			if(check.getUsername().equals(username)) {
				if(check.getPassword() == password) {
					return check;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * GET actor with a certain id
	 * @param id
	 * @return
	 */
	@GetMapping("/actors/{id}")
	public Actor findActorById(@PathVariable int id) {
		return actorRepository.findById(id);
	}

	@GetMapping("/actors/{id}/tickets")
	public List<Ticket> getTickets(@PathVariable int id) {
		
		Actor actor = actorRepository.findById(id);
		
		if(actor == null) return null;
		
		return actor.getTickets();
	}
}
