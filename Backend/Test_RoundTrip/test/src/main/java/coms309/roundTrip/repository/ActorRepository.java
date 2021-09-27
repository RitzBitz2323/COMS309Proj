package coms309.roundTrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import coms309.roundTrip.model.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
	
	Actor findById(int id);
	
	
}
