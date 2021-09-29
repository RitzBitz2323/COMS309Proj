package coms309.roundTrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.roundTrip.model.Ticket;
import coms309.roundTrip.repository.TicketRepository;

@RestController
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;

	@GetMapping("/tickets")
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}
	
	@PostMapping("/tickets")
	public Ticket createTicket(@RequestBody Ticket ticket) {
		if(ticket == null) return null;
		ticketRepository.save(ticket);
		return ticket;
	}
	
}
