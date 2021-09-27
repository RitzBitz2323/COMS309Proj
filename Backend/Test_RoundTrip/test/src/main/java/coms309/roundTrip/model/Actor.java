package coms309.roundTrip.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="actors")
public class Actor {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
		
	private Integer id;
	private String first_name;
	private String last_name;
	private Integer password;
	private Integer user_type;
	private Integer[] ticket_ids;
	private Integer num_of_tickets;
	private Float rating;
	
	public Actor() {}
	
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
	
	public void setPassword(int i) {
		this.password = i;
	}
	
	public int getPassword() {
		return this.password;
	}
	
	public void setUserType(int type) {
		this.user_type = type;
	}
	
	public int getUserType() {
		return this.user_type;
	}
	
	public void setTicketIds(Integer[] ids) {
		this.ticket_ids = ids;
	}
	
	public Integer[] getTicketIds() {
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
