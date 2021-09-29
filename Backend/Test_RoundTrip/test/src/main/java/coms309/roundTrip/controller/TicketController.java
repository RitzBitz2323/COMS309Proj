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
	
	@PostMapping("/tickets")
	public Ticket createTicket(@RequestBody Ticket ticket) {
		if(ticket == null) return null;
		ticketRepository.save(ticket);
		return ticket;
	}
	
	@PutMapping("/tickets/{id}")
	public String updateTechnicianId(@PathVariable int id, @RequestParam(name="tech_id") int tech_id) {
		Ticket ticket = ticketRepository.getById(id);
		Actor tech = actorRepository.getById(tech_id);
		if(ticket == null) return "{\"text\":\"No Ticket has that id.\"}";
		if(tech.getUserType() != Actor.TECHNICIAN) return "{\"text\":\"That actor is not a technician.\"}";
		ticket.setTechnician(tech);
		ticketRepository.save(ticket);
		return String.format("{\"text\":\"updated ticket %o with tech_id %o.\"}", id, tech_id);
	}
	
}
