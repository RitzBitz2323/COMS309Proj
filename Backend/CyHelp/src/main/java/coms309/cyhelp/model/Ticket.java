package coms309.cyhelp.model;

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
	public static final int UNACCEPTED = 0;
	public static final int PENDING = 1;
	public static final int ACCEPTED = 2;
	public static final int CLOSED = 3;
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String title;
	private String description;
	private String[] tags;
	private int state;
	private double latitude;
	private double longitude;
	private String address;
	private float rating;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	@JsonIgnore
	private Actor customerActor;
	
	@ManyToOne
	@JoinColumn(name="technician_id")
	@JsonIgnore
	private Actor technicianActor;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	@JsonIgnore
	private Category categoryObj;
	
	
	// CONSTRUCTORS
	
	public Ticket() {}
	
	public Ticket(String title, String description, Actor customer, double latitude, double longitude) {
		this.title = title;
		this.description = description;
		this.customerActor = customer;
		this.state = Ticket.UNACCEPTED;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	// GETTER AND SETTERS
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
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
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setLatitude(double x) {
		this.latitude = x;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public void setLongitude(double x) {
		this.longitude = x;
	}
	
	public double getLongitude() {
		return this.longitude;
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
	
	public void setCategory(Category cat) {
		this.categoryObj = cat;
	}
	
	public Category getCategory() {
		return this.categoryObj;
	}
	
	@Override
	public String toString() {
		String str = "Title: " + this.getTitle();
		str += "\n" + "ID: " + this.getId();
		str += "\n" + (this.getCustomer() == null ? "NULL" : this.getCustomer().toString());
		str += " --> " + (this.getTechnician() == null ? "NULL" : this.getTechnician().toString());
		return str;
	}
}
