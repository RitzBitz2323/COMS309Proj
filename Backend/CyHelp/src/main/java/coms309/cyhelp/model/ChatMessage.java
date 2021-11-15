package coms309.cyhelp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ChatMessage {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="ticket_id")
	private Ticket ticket;
	
	private String message;
	private int sender;
	
	public ChatMessage() {};
	
	public ChatMessage(int id, Ticket ticket, String msg) {
		this.message = msg;
		this.ticket = ticket;
		this.sender = id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String str) {
		message = str;
	}
	
	public int getSender() {
		return sender;
	}
	
	public void setSender(int id) {
		sender = id;
	}
	
	public Ticket getTicket() {
		return ticket;
	}
	
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
}
