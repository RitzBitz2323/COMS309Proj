package coms309.cyhelp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Ticket;
import coms309.cyhelp.repository.ActorRepository;

@Service
public class ActorService {

	@Autowired
	ActorRepository actorRepository;
	
	public Actor createActor(Actor actor) {
		if(findByUsername(actor.getUsername()) == null) {
			actorRepository.save(actor);
			return actor;
		}
		return null;
	}
	
	public Actor updateActor(Actor actor) {
		actorRepository.save(actor);
		return actor;
	}
	
	public List<Actor> findAllActors() {
		return actorRepository.findAll();
	}
	
	public Actor findByUsername(String username) {
		return actorRepository.findByUsername(username);
	}
	
	public Actor findByUsernameAndPassword(String username, long password) {
		return actorRepository.findByUsernameAndPassword(username, password);
	}
	
	public Actor findById(int id) {
		return actorRepository.findById(id);
	}

	public List<Ticket> getActorTickets(Actor actor) {
		return actor.getTickets();
	}
}
