package coms309.cyhelp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public HashMap<String, String> updateRating(double rate, int userID) {
		HashMap<String, String> result = new HashMap<String, String>();
		if(rate < 0) {
			result.put("message", "Inputted number is not an acceptable value");
			return result;
		};
		
		Rating rating = ratingRepository.getById(userID);
		rating.updateRating(rate);
		result.put("Message", ("Updated rating. Rating now" + rating.getRating()));
		return result;
	}
	
	public String clearRating(int id) {
		ratingRepository.getById(id).setRating(0);
		return ("Cleared rating of user " + id + " to rating of " + ratingRepository.getById(id).getRating());
		
	}
	
	
	public HashMap<String, String> deleteRatingById(int id) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		Rating rating = ratingRepository.findById(id);
		if(rating == null) {
			result.put("message", "Could not find rating for specified user");
			return result;
		}
		
		ratingRepository.delete(rating);
		result.put("message", "Successfully deleted rating associated with user");
		
		return result;
	}
}
