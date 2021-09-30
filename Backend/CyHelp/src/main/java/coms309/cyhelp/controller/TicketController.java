package coms309.cyhelp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Ticket;
import coms309.cyhelp.repository.ActorRepository;
import coms309.cyhelp.repository.TicketRepository;

@RestController
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	ActorRepository actorRepository;
	

	/**
	 * Gets all of the tickets in the repository.
	 * @return
	 */
	@GetMapping("/tickets")
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}
	
	/**
	 * Returns the ticket with the specified id. 
	 * @param id
	 * @return
	 */
	@GetMapping("/tickets/{id}")
	public Ticket getTicket(@PathVariable int id) {
		return ticketRepository.findById(id);
	}
	
	/**
	 * Creates a ticket.
	 * @param ticket
	 * @return
	 */
	@PostMapping("/tickets")
	public String createTicket(@RequestBody Ticket ticket) {
		
		if(ticket == null) return "{\"message\":\"POST was not a ticket object.\"}";
		
		ticketRepository.save(ticket);
		
		// get user that created the ticket and append
		// the ticket id to their ticket list.
		Actor customer = actorRepository.findById(ticket.getCustomer().getId());
		customer.addTicket(ticket);
		actorRepository.save(customer);
		
		return ticket.toString();
	}
	
	/**
	 * When the technician accepts a ticket, the ticket is updated
	 * to hold the technician's id. 
	 * @param id
	 * @param technician
	 * @return
	 */
	@PutMapping("/tickets/{id}/accept")
	public String updateTechnicianId(@PathVariable int id, @RequestBody Actor technician) {
		
		Ticket ticket = ticketRepository.findById(id);
		Actor tech = actorRepository.findById(technician.getId());
		
		if(ticket == null) return String.format("{\"message\":\"ticket(id=%o) doesn't exist.\"}", id);
		if(tech == null) return "{\"message\":\"not a valid technician.\"}";
		if(tech.getUserType() != Actor.TECHNICIAN) return "{\"message\":\"this actor is not a technician.\"}";
		
		// remove the ticket id from the ticket_list of the previous technician
		Actor prevTech = ticket.getTechnician();
		if(prevTech != null) prevTech.removeTicket(ticket);
		
		ticket.setTechnician(tech);
		ticket.setState(Ticket.PENDING);
		
		// returns true if this technician has not been assigned to this ticket
		boolean assigned = tech.addTicket(ticket);
		if(!assigned) return "{\"message\":\"this technician has been already assigned to this ticket.\"}";
		
		ticketRepository.save(ticket);
		actorRepository.save(tech);
		
		return String.format("{\"message\":\"ticket %o was accepted by tech_id %o.\"}", id, technician.getId());
	}
	
}
