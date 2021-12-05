package coms309.cyhelp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Ticket;
import coms309.cyhelp.service.ActorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "ActorController", description = "Rest Controller for the Actor Entity.")
@RestController
public class ActorController {

	@Autowired
	ActorService actorService;
	
	@ApiOperation(value = "Gets the list of all actors.")
	@GetMapping("/actors")
	public @ResponseBody List<Actor> getActors() {
		return actorService.findAllActors();
	}
	
	/**
	 * Checks if an actor with a certain username exists.
	 * @param username
	 * @return
	 */
	@ApiOperation(value = "Checks if an actor with a certain username exists.")
	@GetMapping("/actors/exists")
	public @ResponseBody Actor checkUserExists(@RequestParam(value="username", required = true) String username) {
		return actorService.findByUsername(username); 
	}
	
	/**
	 * Creates a Actor
	 * @param actor
	 * @return
	 */
	@ApiOperation(value = "Creates a new actor.")
	@PostMapping("/actors")
	public @ResponseBody Actor addActor(@RequestBody Actor actor) {
		
		if(actor == null) return null;
		
		return actorService.createActor(actor);
	}
	
	/**
	 * Updates an actor.
	 * @param actor
	 * @return
	 */
	@ApiOperation(value = "Updates the actor and save the new data.")
	@PutMapping("/actors")
	public @ResponseBody Actor updateActor(@RequestBody Actor actor) {
		
		if(actor == null) return null;
		if(actorService.findById(actor.getId()) == null) return null;
		
		return actorService.updateActor(actor);
	}
	
	@ApiOperation(value = "Checks if the provided username and password is correct and returns the corresponding actor.")
	@PostMapping("/actors/login")
	public @ResponseBody Actor actorLogin(@RequestBody Actor actor) {
		return actorService.findByUsernameAndPassword(actor.getUsername(), actor.getPassword());
	}
	
	/**
	 * GET actor with a certain id
	 * @param id
	 * @return
	 */
	@ApiOperation("Gets the actor with the specified id.")
	@GetMapping("/actors/{id}")
	public @ResponseBody Actor findActorById(@PathVariable int id) {
		return actorService.findById(id);
	}

	/**
	 * Get the tickets of the specified Actor.
	 * @param id
	 * @return
	 */
	@ApiOperation("Returns the list of tickets that the actor with a certain id is associated with.")
	@GetMapping("/actors/{id}/tickets")
	public @ResponseBody List<Ticket> getTickets(@PathVariable int id) {
		
		Actor actor = actorService.findById(id);
		if(actor == null) return null;
		
		return actorService.getActorTickets(actor);
	}
}
