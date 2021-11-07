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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "TicketController", description = "Rest Controller for the Ticket Entity.")
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
	@ApiOperation(value = "Returns a list containing all of the tickets ")
	@GetMapping("/tickets")
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}
	
	@ApiOperation(value = "Returns a list of tickets that are within a certain distance.")
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
	@ApiOperation(value = "Get the ticket with the specified id.")
	@GetMapping("/tickets/{id}")
	public Ticket getTicket(@PathVariable int id) {
		return ticketRepository.findById(id);
	}
	
	/**
	 * Creates a ticket.
	 * @param ticket
	 * @return String
	 */
	@ApiOperation(value = "Creates a new ticket using the data from the RequestBody.")
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
	@ApiOperation(value = "The provided technician object accepts the ticket with the specified id.")
	@PutMapping("/tickets/{id}/accept")
	public HashMap<String, String> updateTechnicianId(@PathVariable int id, @RequestBody Actor technician) {
		
		Ticket ticket = ticketRepository.findById(id);
		Actor tech = actorRepository.findById(technician.getId());
		HashMap<String, String> result = new HashMap<String, String>();
		
		if(ticket == null) {
			result.put("message", String.format("ticket(id=%o) doesn't exist.", id));
			return result ;
		}
		if(tech == null) {
			result.put("message", "not a valid technician.");
			return result;
		}
		if(tech.getUserType() != Actor.TECHNICIAN) {
			result.put("message", "this actor is not a technician.");
			return result;
		}
		
		// de-associate this ticket from the previous technician (if there is one)
		Actor prevTech = ticket.getTechnician();
		if(prevTech != null) {
			prevTech.removeTicket(ticket);
			actorRepository.save(prevTech);
		}
		
		// associate the ticket to this technician
		ticket.setTechnician(tech);
		ticket.setState(Ticket.PENDING);
		
		// assign the technician to this ticket
		tech.addTicket(ticket);
		
		// save changes
		ticketRepository.save(ticket);
		actorRepository.save(tech);
		
		result.put("message", "success");
		return result;
	}
	
	/**
	 * Deletes a ticket based on the ticket provided by
	 * the RequestBody.
	 * @param ticket
	 * @return String
	 */
	@ApiOperation(value = "Deletes the ticket.")
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
	@ApiOperation(value = "Deletes a ticket with a certain id.")
	@DeleteMapping("/tickets/{id}")
	public Map<String, String> deleteByPathVariable(@PathVariable int id) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		// try to find the desired ticket
		Ticket ticket = ticketRepository.findById(id);
		if(ticket == null) {
			result.put("message", "that ticket doesn't exist.");
			return result;
		}
		
		Actor customer = ticket.getCustomer();
		Actor technician = ticket.getTechnician();
		Category category = ticket.getCategory();
		
		// disconnect the customer from this ticket
		if(customer != null) {
			customer.removeTicket(ticket);
			actorRepository.save(customer);
		}
		
		// disconnect the technician from this ticket
		if(technician != null) {
			technician.removeTicket(ticket);
			actorRepository.save(technician);
		}
		
		// disconnect the category from this ticket
		if(category != null) {
			category.removeTicket(ticket);
			categoryRepository.save(category);
		}
		
		ticketRepository.delete(ticket);
		result.put("message", "success");
		
		return result;
	}
	
}
