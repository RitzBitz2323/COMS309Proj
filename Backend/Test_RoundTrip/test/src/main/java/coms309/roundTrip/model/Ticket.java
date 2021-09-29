package coms309.roundTrip.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="Tickets")
public class Ticket {
	
	// state values of the ticket
	public static final int OPEN = 0;
	public static final int PENDING = 1;
	public static final int CLOSED = 2;
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String title;
	private String description;
	private String[] tags;
	private int state;                // 0 = OPEN, 1 = PENDING, 2 = CLOSED
	private double[] location;
	private float rating;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	@JsonIgnore
	private Actor customerActor;
	
	@ManyToOne
	@JoinColumn(name="technician_id")
	@JsonIgnore
	private Actor technicianActor;
	
	@Column(name = "category")
	private String category;
	
	
	// CONSTRUCTORS
	
	public Ticket() {}
	
	public Ticket(String title, String description, Actor customer, double[] location) {
		this.title = title;
		this.description = description;
		this.customerActor = customer;
		this.location = location;
	}
	
	
	// GETTER AND SETTERS
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setCategory(String cat) {
		this.category = cat;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setTags(String[] args) {
		this.tags = args;
	}
	
	public String[] getTags() {
		return this.tags;
	}
	
	public void setState(int s) {
		this.state = s;
	}
	
	public int getState() {
		return this.state;
	}
	
	public void setLocation(double[] loc) {
		this.location = loc;
	}
	
	public double[] getLocation() {
		return this.location;
	}
	
	public void setRating(float rating) {
		this.rating = rating;
	}
	
	public float getRating() {
		return this.rating;
	}
	
	// FOREGIN KEYS
	public void setCustomer(Actor actor) {
		this.customerActor = actor;
	}
		
	public Actor getCustomer() {
		return this.customerActor;
	}
		
	public void setTechnician(Actor actor) {
		this.technicianActor = actor;
	}
		
	public Actor getTechnician() {
		return this.technicianActor;
	}
}
