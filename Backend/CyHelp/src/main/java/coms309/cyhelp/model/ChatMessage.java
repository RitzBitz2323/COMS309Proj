package coms309.cyhelp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ChatMessage {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name="ticket_id")
	private Ticket ticket;
	
	private String message;
	private int user_id;
	
	public ChatMessage(int id, Ticket ticket, String msg) {
		this.message = msg;
		this.ticket = ticket;
		this.user_id = id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String str) {
		message = str;
	}
	
	public int getUserId() {
		return user_id;
	}
	
	public void setUserId(int id) {
		user_id = id;
	}
	
	public Ticket getTicket() {
		return ticket;
	}
	
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
}
