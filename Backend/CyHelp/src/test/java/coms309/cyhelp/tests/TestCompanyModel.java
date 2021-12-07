package coms309.cyhelp.tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import static java.util.Arrays.asList;
import org.junit.jupiter.api.Test;


import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Company;

public class TestCompanyModel{
	@Test
	public void testUpdateRating() {
		Company temp = new Company("Sinoloa Cartel", "Bill", 12345);
	
		assertEquals(0, temp.getId());
		assertEquals("Sinoloa Cartel", temp.getName());
		assertEquals(12345, temp.getPassword());
		
		Actor tempOne = new Actor(1, "Joey", "Capone", "JCapone", 123456); 
		Actor tempTwo = new Actor(1, "Marcus", "Escobar", "MEscobar", 123456); 
		Actor tempThree = new Actor(1, "Jeff", "Gambino", "JGambino", 123456); 
		temp.addEmployees(tempOne);
		temp.addEmployees(tempTwo);
		temp.addEmployees(tempThree);
		
		List<Actor> employees = asList(tempOne, tempTwo, tempThree);
		assertTrue(temp.getEmployees().equals(employees));
		 
	}
}
