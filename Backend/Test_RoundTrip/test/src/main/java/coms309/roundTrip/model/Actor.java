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
		
	private int id;
	private String first_name;
	private String last_name;
	private String password;
	private int user_type;
	private String ticket_ids;
	private int num_of_tickets;
	private int rating;
	
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
	
	public void setUserType(int type) {
		this.user_type = type;
	}
	
	public int getUserType() {
		return this.user_type;
	}
	
}
