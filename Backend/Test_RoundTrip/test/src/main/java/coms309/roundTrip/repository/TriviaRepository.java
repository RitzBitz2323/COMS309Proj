package coms309.roundTrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms309.roundTrip.model.Trivia;

public interface TriviaRepository extends JpaRepository<Trivia, Long>{

}
