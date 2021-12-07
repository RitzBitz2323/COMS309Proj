package coms309.cyhelp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="Actors")
public class Actor {
	
	// user types
	public static final int USER = 0;
	public static final int TECHNICIAN = 1;
	public static final int COMPANY = 2;
	public static final int ADMINISTRATOR = 3;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	@ApiModelProperty(notes = "First name of the Actor")
	private String first_name;
	@ApiModelProperty(notes = "Last name of the Actor")
	private String last_name;
	@ApiModelProperty(notes = "The username the Actor")
	private String username;
	@ApiModelProperty(notes = "The hashCode representation of the string password of the Actor")
	private long password;
	@ApiModelProperty(notes = "The type of Actor: 0 = USER, 1 = TECHNICIAN, 2 = COMPANY, 3 = ADMINISTRATOR")
	private int user_type;
	@ApiModelProperty(notes = "The average rating of the actor.")
	private float rating;
	@ApiModelProperty(notes = "The home address of the actor.")
	private String home_address;
	
	// list of tickets that this actor is the customer of
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	@ApiModelProperty(notes = "List of tickets that this actor is the customer.")
	private List<Ticket> customerTickets; 
	
	// list of tickets that this actor is the technician of
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	@ApiModelProperty(notes = "List of tickets that this actor is the technician.")
	private List<Ticket> technicianTickets;
	
	@ManyToOne
	@JsonIgnore
	@ApiModelProperty(notes = "The Company that this Actor is associated with.")
	private Company company;
	
	public Actor() {
		this.customerTickets = new ArrayList<>();
		this.technicianTickets = new ArrayList<>();
		this.rating = 5;
		this.home_address = "";
	}
	
	public Actor(int type, String firstName, String lastName, String username, long password) {
		this();
		this.user_type = type;
		this.first_name = firstName;
		this.last_name = lastName;
		this.username = username;
		this.password = password;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Company getCompany() {
		return this.company;
	}
	
	public void setCustomerTickets(List<Ticket> customerTickets) {
		this.customerTickets = customerTickets;
	}
	
	public List<Ticket> getCustomerTickets() {
		return this.customerTickets;
	}
	
	public void setTechnicianTickets(List<Ticket> techTickets) {
		this.technicianTickets = techTickets;
	}
	
	public List<Ticket> getTechnicianTickets() {
		return this.technicianTickets;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
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
	
	public void setRating(float n) {
		this.rating = n;
	}
	
	public float getRating() {
		return this.rating;
	}
	
	public void setHomeAddress(String address) {
		this.home_address = address;
	}
	
	public String getHomeAddress() {
		return this.home_address;
	}
	
	@Override
	public String toString() {
		String[] user_types = {"USER", "TECHNICIAN", "COMPANY", "ADMINISTRATOR"};
		return user_types[this.getUserType()] + "(id=" + this.getId() + ")";
	}
	
	public boolean addTicket(Ticket ticket) {
		
		switch(this.getUserType()) {
		
		case Actor.USER:
			this.customerTickets.add(ticket);
			return true;
			
		case Actor.TECHNICIAN:
			this.technicianTickets.add(ticket);
			return true;
		}
		
		return false;
	}
	
	public boolean removeTicket(Ticket ticket) {
		
		switch(this.getUserType()) {
		
		case Actor.USER:
			this.customerTickets.remove(ticket);
			return true;
			
		case Actor.TECHNICIAN:
			this.technicianTickets.remove(ticket);
			return true;
		}
		
		return false;
	}
	
	@JsonIgnore
	public List<Ticket> getTickets() {
		
		if(this.getUserType() == Actor.TECHNICIAN)
			return this.technicianTickets;
		return this.customerTickets;
	}
	
}
