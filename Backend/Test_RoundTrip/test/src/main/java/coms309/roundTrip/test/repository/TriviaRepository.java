package coms309.roundTrip.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms309.roundTrip.test.model.Trivia;

public interface TriviaRepository extends JpaRepository<Trivia, Long>{

}
