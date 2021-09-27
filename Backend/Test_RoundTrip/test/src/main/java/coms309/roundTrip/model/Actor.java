package coms309.roundTrip.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Actors")
public class Actor {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String first_name;
	private String last_name;
	private long password;
	private int user_type;
	private int[] ticket_ids;
	private int num_of_tickets;
	private float rating;
	
	public Actor() {}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}
	
	public String getFirstName() {
		return this.first_name;
	}
	
	public void setLastName(String last_name) {
		this.last_name = last_name;
	}
	
	public String getLastName() {
		return this.last_name;
	}
	
	public void setPassword(long password) {
		this.password = password;
	}
	
	public long getPassword() {
		return this.password;
	}
	
	public void setUserType(int type) {
		this.user_type = type;
	}
	
	public int getUserType() {
		return this.user_type;
	}
	
	public void setTicketIds(int[] ids) {
		this.ticket_ids = ids;
	}
	
	public int[] getTicketIds() {
		return this.ticket_ids;
	}
	
	public void setNumOfTickets(int n) {
		this.num_of_tickets = n;
	}
	
	public int getNumOfTickets() {
		return this.num_of_tickets;
	}
	
	public void setRating(float n) {
		this.rating = n;
	}
	
	public float getRating() {
		return this.rating;
	}
	
}
