package coms309.roundTrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.roundTrip.model.Actor;
import coms309.roundTrip.repository.ActorRepository;

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
		actorRepository.save(actor);
		return actor;
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
}
