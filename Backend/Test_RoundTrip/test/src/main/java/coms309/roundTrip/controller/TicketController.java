package coms309.roundTrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coms309.roundTrip.model.Actor;
import coms309.roundTrip.model.Ticket;
import coms309.roundTrip.repository.ActorRepository;
import coms309.roundTrip.repository.TicketRepository;

@RestController
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	ActorRepository actorRepository;
	

	@GetMapping("/tickets")
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}
	
	@GetMapping("/tickets/{id}")
	public Ticket getTicket(@PathVariable int id) {
		return ticketRepository.getById(id);
	}
	
	
	@PostMapping("/tickets")
	public Ticket createTicket(@RequestBody Ticket ticket) {
		if(ticket == null) return null;
		ticketRepository.save(ticket);
		return ticket;
	}
	
	@PutMapping("/tickets/{id}/tech")
	public String updateTechnicianId(@PathVariable int id, @RequestBody Actor technician) {
		
		Ticket ticket = ticketRepository.getById(id);
		Actor tech = actorRepository.getById(technician.getId());
		
		if(ticket == null) return "{\"message\":\"no ticket has that id.\"}";
		if(tech.getUserType() != Actor.TECHNICIAN) return "{\"message\":\"that actor is not a technician.\"}";
		
		ticket.setTechnician(tech);
		ticketRepository.save(ticket);
		
		return String.format("{\"message\":\"updated ticket %o with tech_id %o.\"}", id, technician.getId());
	}
	
}
