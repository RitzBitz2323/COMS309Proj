package coms309.cyhelp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Ticket;

public class TestActorModel {

	@Test
	public void testActorAddTicket() {
		
		Actor user = new Actor(Actor.USER, "Billy", "Bob", "bb309", 12345);
		Ticket ticket = mock(Ticket.class);
		when(ticket.getTitle()).thenReturn("Broken AC");
		
		user.addTicket(ticket);
		
		assertEquals(1, user.getTickets().size());
		assertEquals(0, user.getTechnicianTickets().size());
		assertEquals("Broken AC", user.getTickets().get(0).getTitle());
		assertEquals("Broken AC", user.getCustomerTickets().get(0).getTitle());
	}
	
	@Test
	public void testActorRemoveTicket() {
		
		Actor user = new Actor(Actor.USER, "Billy", "Bob", "bb309", 12345);
		
		Ticket ticket = mock(Ticket.class);
		when(ticket.getDescription()).thenReturn("no description.");
		
		ArrayList<Ticket> list = new ArrayList<Ticket>();
		list.add(ticket);
		user.setCustomerTickets(list);
		
		assertEquals(1, user.getCustomerTickets().size());
		assertEquals("no description.", user.getTickets().get(0).getDescription());
		
		user.removeTicket(ticket);
		
		assertEquals(0, user.getTickets().size());
	}
}
