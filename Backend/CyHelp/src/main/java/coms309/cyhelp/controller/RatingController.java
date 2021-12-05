package coms309.cyhelp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Rating;
import coms309.cyhelp.model.Tags;
import coms309.cyhelp.repository.RatingRepository;
import coms309.cyhelp.service.ActorService;
import coms309.cyhelp.service.RatingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "RatingController", description = "Rest Controller for the Rating Entity.")
@RestController
public class RatingController {
	@Autowired
	RatingService ratingService;
	
	/**
	 * Gets all of the ratings in the repository.
	 * @return list of ratings
	 */
	@ApiOperation(value="To obtain the list of all ratings within the database.")
	@GetMapping("/ratings")
	public @ResponseBody List<Rating> getAllRatings() {
		return ratingService.getAllRatings();
	}
	
	@GetMapping("/ratings/{id}")
	public Rating getRating(@PathVariable int id) {
		return ratingService.getRating(id);
	}
	
	@PostMapping("/ratings")
	public @ResponseBody Rating createRating(@RequestBody Rating input_rating) {
		return ratingService.createRating(input_rating);
	}
	
	@PutMapping("/ratings/{id}/update")
	public HashMap<String, String> updateRating(@RequestBody double rate, @PathVariable int id) {
		
		return ratingService.updateRating(rate, id);
	}
	
	@PutMapping("/ratings/{id}/clear")
	public String clearRating(@PathVariable int id) {
		return ratingService.clearRating(id);
	}
	
	@DeleteMapping("ratings/{id}")
	public Map<String, String> deleteByPathVariable(@PathVariable int id) {
		return ratingService.deleteRatingById(id);
	}
}
