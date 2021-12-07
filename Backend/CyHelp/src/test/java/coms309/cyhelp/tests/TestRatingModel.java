package coms309.cyhelp.tests;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import coms309.cyhelp.model.Rating;

public class TestRatingModel {
	@Test
	public void testUpdateRating() {
		Rating temp = new Rating(0);
	
		assertEquals(0, temp.getId());
		assertEquals(5.0, temp.getRating());
		temp.updateRating(2);
		assertEquals(3.5, temp.getRating());
	}
}
