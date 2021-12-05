package coms309.cyhelp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Category;
import coms309.cyhelp.model.Ticket;
import coms309.cyhelp.repository.ActorRepository;
import coms309.cyhelp.repository.CategoryRepository;
import coms309.cyhelp.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	ActorRepository actorRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public List<Ticket> findAllTickets() {
		return ticketRepository.findAll();
	}
	
	public Ticket findById(int id) {
		return ticketRepository.findById(id);
	}
	
	public List<Ticket> findTicketsNear(double latitude, double longitude, double range) {
		
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
	
	
	public HashMap<String, String> acceptTicket(int id, Actor technician) {
		
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
		actorRepository.save(tech);
		ticketRepository.save(ticket);
		
		result.put("message", "success");
		return result;
	}
	
	public Ticket closeTicket(int id) {
		if(id < 0) return null;
		
		Ticket ticket = ticketRepository.findById(id);
		ticket.setState(3);
		ticketRepository.save(ticket);
		return ticket;
		
		
	}
	
	
	
	
	public Ticket createTicket(Ticket ticket) {
		
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
	
	
	public HashMap<String, String> deleteTicket(Ticket ticket) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		if(ticket == null) {
			result.put("message", "that ticket doesn't exist.");
			return result;
		}
		
		return deleteById(ticket.getId());
	}
	
	public HashMap<String, String> deleteById(int id) {
		
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
