package coms309.cyhelp.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.Ticket;
import coms309.cyhelp.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "TicketController", description = "Rest Controller for the Ticket Entity.")
public class TicketController {
	
	@Autowired
	TicketService ticketService;

	/**
	 * Gets all of the tickets in the repository.
	 * @return
	 */
	@ApiOperation(value = "Returns a list containing all of the tickets ")
	@GetMapping("/tickets")
	public @ResponseBody List<Ticket> getAllTickets() {
		
		return ticketService.findAllTickets();
	}
	
	@ApiOperation(value = "Returns a list of tickets that are within a certain distance.")
	@GetMapping("/tickets/at")
	public @ResponseBody List<Ticket> getNearLocation(@RequestParam(value="lat", required=true) double latitude, @RequestParam(value="long", required=true) double longitude, @RequestParam(value="range", required=false) double range) {
		
		return ticketService.findTicketsNear(latitude, longitude, range);
	}
	
	/**
	 * Returns the ticket with the specified id. 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Get the ticket with the specified id.")
	@GetMapping("/tickets/{id}")
	public @ResponseBody Ticket getTicket(@PathVariable int id) {
		return ticketService.findById(id);
	}
	
	/**
	 * Creates a ticket.
	 * @param ticket
	 * @return String
	 */
	@ApiOperation(value = "Creates a new ticket using the data from the RequestBody.")
	@PostMapping("/tickets")
	public @ResponseBody Ticket createTicket(@RequestBody Ticket ticket) {
		return ticketService.createTicket(ticket);
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
		
		return ticketService.acceptTicket(id, technician);
	}
	
	/**
	 * Close ticket with id
	 * @param id
	 * @return ticket
	 */
	@ApiOperation(value = "Closes the ticket")
	@PutMapping("/tickets/{id}/close")
	public Ticket closeTicket(@PathVariable int id) {
		
		return ticketService.closeTicket(id);
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
		
		return ticketService.deleteTicket(ticket);
	}
	
	/**
	 * Deletes the ticket with the specified id.
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Deletes a ticket with a certain id.")
	@DeleteMapping("/tickets/{id}")
	public Map<String, String> deleteByPathVariable(@PathVariable int id) {
		
		return ticketService.deleteById(id);
	}
	
}
