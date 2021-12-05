package coms309.cyhelp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms309.cyhelp.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

		Rating findById(int id);
}
