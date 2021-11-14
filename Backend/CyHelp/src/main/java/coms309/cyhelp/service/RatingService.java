package coms309.cyhelp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms309.cyhelp.model.Rating;
import coms309.cyhelp.model.Tags;
import coms309.cyhelp.repository.RatingRepository;

@Service
public class RatingService {
	@Autowired
	RatingRepository ratingRepository;	
	
	public List<Rating> getAllRatings() {
		return ratingRepository.findAll();
	}
		
	public Rating getRating(int id) {
		return ratingRepository.findById(id);
	}
	
	public Rating createRating(Rating rating) {
		if(rating == null) return null;
				
		Rating newRating = new Rating(rating.getId());
		ratingRepository.save(newRating);
		
		return newRating;
	}
	
	public String updateRating(double rate, int userID) {
		if(rate < 0) return ("Inputted number is not an acceptable value ");
		
		Rating rating = ratingRepository.getById(userID);
		rating.updateRating(rate);
		return ("Updated rating. Rating now" + rating.getRating());
	}
	
	public String clearRating(int id) {
		ratingRepository.getById(id).setRating(0);
		return ("Cleared rating of user " + id + " to rating of " + ratingRepository.getById(id).getRating());
		
	}
	
	
	public String deleteRatingById(int id) {
		
		Rating rating = ratingRepository.findById(id);
		if(rating == null) {
			return ("Could not find rating for specified user");
		}
		
		ratingRepository.delete(rating);
		return ("Successfully deleted rating associated with user");
	}
}
