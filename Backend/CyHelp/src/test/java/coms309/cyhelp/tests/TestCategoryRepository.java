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
		when(categoryRepository.findById(0)).thenReturn(new Category("HVAC"));
		
		Ticket ticket1 = mock(Ticket.class);
		when(ticket1.getTitle()).thenReturn("Laptop Screen Broken");
		when(ticket1.getDescription()).thenReturn("Played football with my mac and accidentally broke it");
		when(ticket1.getCustomer()).thenReturn(null);
		when(ticket1.getLatitude()).thenReturn(19.0);
		when(ticket1.getLongitude()).thenReturn(42.0);
		
		Ticket ticket2 = mock(Ticket.class);
		when(ticket1.getTitle()).thenReturn("Sink pipes burst");
		when(ticket1.getDescription()).thenReturn("Tried cleaning the hood of my car in the sink");
		when(ticket1.getCustomer()).thenReturn(null);
		when(ticket1.getLatitude()).thenReturn(21.0);
		when(ticket1.getLongitude()).thenReturn(29.0);
		
		Ticket ticket3 = mock(Ticket.class);
		when(ticket1.getTitle()).thenReturn("Patio on fire");
		when(ticket1.getDescription()).thenReturn("Accidentally lit my patio on fire");
		when(ticket1.getCustomer()).thenReturn(null);
		when(ticket1.getLatitude()).thenReturn(44.0);
		when(ticket1.getLongitude()).thenReturn(-20.0);
		
		Category categoryTest = categoryRepository.findById(0);
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
