package coms309.cyhelp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Ticket;
import coms309.cyhelp.repository.TicketRepository;

public class TestTicketRepository {
		
	@Test
	public void testFindById() {
		
		TicketRepository ticketRepository = mock(TicketRepository.class);
		when(ticketRepository.findById(1)).thenReturn(new Ticket("Broken AC", "My AC stopped working.", null, 10.0, 20.0));
		
		Actor customer = mock(Actor.class);
		when(customer.getFirstName()).thenReturn("Billy");
		when(customer.getLastName()).thenReturn("Bob");
		when(customer.getPassword()).thenReturn((long) 12345);

		Ticket ticket = ticketRepository.findById(1);
		ticket.setCustomer(customer);

		assertEquals("Billy", ticket.getCustomer().getFirstName());
		assertEquals("Bob", ticket.getCustomer().getLastName());
		assertEquals("Broken AC", ticket.getTitle());
		assertEquals(10.0, ticket.getLatitude());
	}

	@Test
	public void testFindAll() {
		
		ArrayList<Ticket> list = new ArrayList<Ticket>();
		Ticket ticket1 = new Ticket("Broken AC", "My AC stopped working.", null, 12.0, 4.0);
		Ticket ticket2 = new Ticket("Toilet", "Toilet not flushing.", null, 42.0, 32.0);
		Ticket ticket3 = new Ticket("Lights", "Lights don't turn on.", null, 34.0, -10.0);
		list.add(ticket1);
		list.add(ticket2);
		list.add(ticket3);
		
		TicketRepository ticketRepository = mock(TicketRepository.class);
		when(ticketRepository.findAll()).thenReturn(list);
		
		List<Ticket> tickets = ticketRepository.findAll();
		
		assertEquals(3, tickets.size());
		assertEquals("Toilet", tickets.get(1).getTitle());
		assertEquals(ticket2.getId(), tickets.get(1).getId());
	}
}
