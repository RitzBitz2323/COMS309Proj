package coms309.cyhelp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Category;
import coms309.cyhelp.model.Ticket;
import coms309.cyhelp.repository.ActorRepository;
import coms309.cyhelp.repository.CategoryRepository;
import coms309.cyhelp.repository.TicketRepository;

@RestController
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	ActorRepository actorRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	

	/**
	 * Gets all of the tickets in the repository.
	 * @return
	 */
	@GetMapping("/tickets")
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}
	
	@GetMapping("/tickets/at")
	public List<Ticket> getNearLocation(@RequestParam(value="lat", required=true) double latitude, @RequestParam(value="long", required=true) double longitude, @RequestParam(value="range", required=false) double range) {
		
		List<Ticket> list = new ArrayList<Ticket>();
		final double c = Math.PI / 180.0; // used to convert latitude and longitude values to radians
		double lat1 = latitude * c;
		double long1 = longitude * c;
		
		if(range == 0.0) {
			range = 50.0;
		}
		
		for(Ticket t : ticketRepository.findAll()) {
			
			// calculate the distance between request's location and ticket's location
			double lat2 = t.getLatitude() * c;
			double long2 = t.getLongitude() * c;
			double a = Math.pow(Math.sin((lat2-lat1)/2.0), 2) + Math.cos(lat1)*Math.cos(lat2)*Math.pow(Math.sin((long2-long1)/2.0), 2);
			double b = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			double dist = 3958.8 * b;
			
			// add ticket to list if within range
			if(dist <= range) list.add(t);
		}
		
		return list;
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
	 * @return String
	 */
	@PostMapping("/tickets")
	public Ticket createTicket(@RequestBody Ticket ticket) {
		
		if(ticket == null) return null;
		
		ticketRepository.save(ticket);
		ticket = ticketRepository.findById(ticket.getId());
		
		
		
		// set the ticket's category to the provided category id
		boolean setToNone = false;
		if(ticket.getCategory() != null) {
			Category category = categoryRepository.findById(ticket.getCategory().getId());
			if(category != null) {
				ticket.setCategory(category);
				category.addTicket(ticket);
				categoryRepository.save(category);
			} else { setToNone = true; }
		} else { setToNone = true; }
		
		// if category is null set it to none
		if(setToNone) {
			Category none = categoryRepository.findById(1);
			ticket.setCategory(none);
			none.addTicket(ticket);
			categoryRepository.save(none);
		}
		

		// get user that created the ticket and append
		// the ticket id to their ticket list.
		Actor customer = actorRepository.findById(ticket.getCustomer().getId());
		if(customer != null) {
			customer.addTicket(ticket);
			ticket.setCustomer(customer);
			actorRepository.save(customer);
		}
		
		// update the technician's ticket list
		// if a technician id is provided.
		if(ticket.getTechnician() != null) {
			Actor technician = actorRepository.findById(ticket.getTechnician().getId());
			if(technician != null) {
				technician.addTicket(ticket);
				ticket.setTechnician(technician);
				actorRepository.save(technician);
			}
		}
		
		ticketRepository.save(ticket);
		
		return ticket;
	}
	
	/**
	 * When the technician accepts a ticket, the ticket is updated
	 * to hold the technician's id. 
	 * @param id
	 * @param technician
	 * @return String
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
		if(prevTech != null) {
			prevTech.removeTicket(ticket);
			actorRepository.save(prevTech);
		}
		
		ticket.setTechnician(tech);
		ticket.setState(Ticket.PENDING);
		
		// returns true if this technician has not been assigned to this ticket
		tech.addTicket(ticket);
		
		ticketRepository.save(ticket);
		actorRepository.save(tech);
		
		return String.format("{\"message\":\"ticket %o was accepted by technician(id=%o).\"}", id, technician.getId());
	}
	
	/**
	 * Deletes a ticket based on the ticket provided by
	 * the RequestBody.
	 * @param ticket
	 * @return String
	 */
	@DeleteMapping("/tickets")
	public Map<String, String> deleteByRequestBody(@RequestBody Ticket ticket) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		if(ticket == null) {
			result.put("message", "that ticket doesn't exist.");
			return result;
		}
		
		return deleteByPathVariable(ticket.getId());
	}
	
	/**
	 * Deletes the ticket with the specified id.
	 * @param id
	 * @return
	 */
	@DeleteMapping("/tickets/{id}")
	public Map<String, String> deleteByPathVariable(@PathVariable int id) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		Ticket ticket = ticketRepository.findById(id);
		if(ticket == null) {
			result.put("message", "that ticket doesn't exist.");
			return result;
		}
		
		Actor customer = ticket.getCustomer();
		Actor technician = ticket.getTechnician();
		Category category = ticket.getCategory();
		
		if(customer != null) {
			customer.removeTicket(ticket);
			actorRepository.save(customer);
		}
		
		if(technician != null) {
			technician.removeTicket(ticket);
			actorRepository.save(technician);
		}
		
		if(category != null) {
			category.removeTicket(ticket);
			categoryRepository.save(category);
		}
		
		ticketRepository.delete(ticket);
		result.put("message", "success");
		
		return result;
	}
	
}
