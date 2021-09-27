package coms309.roundTrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import coms309.roundTrip.model.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
	
	Actor findById(int id);
	
	//@Query("SELECT * FROM actors WHERE actor.first_name = ?1 AND actor.last_name = ?2")
	//Actor findByFirstLastName(String first_name, String last_name);
}
