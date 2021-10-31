package coms309.cyhelp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import coms309.cyhelp.model.Category;
import coms309.cyhelp.model.Ticket;
import coms309.cyhelp.repository.CategoryRepository;

public class TestCategoryRepository {
	@Test
	public void testFindById() {
		
		CategoryRepository categoryRepository = mock(CategoryRepository.class);
		when(categoryRepository.findById(1)).thenReturn(new Category("HVAC"));
		
		Ticket ticket1 = new Ticket("Laptop Screen Broken", "Played football with my mac and accidentally broke it", null, 19.0, 42.0);
		Ticket ticket2 = new Ticket("Sink pipes burst", "Tried cleaning the hood of my car in the sink", null, 21.0, 29.0);
		Ticket ticket3 = new Ticket("Patio on fire", "Accidentally lit my patio on fire", null, 44.0, -20.0);
		
		Category categoryTest = categoryRepository.findById(1);
		categoryTest.addTicket(ticket1);
		categoryTest.addTicket(ticket2);
		categoryTest.addTicket(ticket3);

		assertEquals("HVAC", categoryTest.getTitle());
		assertEquals(1, categoryTest.getId());
		assertEquals("Played football with my mac and accidentally broke it", categoryTest.getTicket(0).getDescription());
		assertEquals(21.0 , categoryTest.getTicket(1).getLatitude());
		assertEquals(null , categoryTest.getTicket(2).getCustomer());
	}

}
