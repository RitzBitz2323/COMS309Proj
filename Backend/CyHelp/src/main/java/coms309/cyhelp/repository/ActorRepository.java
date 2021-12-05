package coms309.cyhelp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms309.cyhelp.model.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
	
	Actor findById(int id);
	Actor findByUsernameAndPassword(String username, long password);
	Actor findByUsername(String username);
	
}
