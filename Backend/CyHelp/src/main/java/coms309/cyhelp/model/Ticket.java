package coms309.cyhelp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	@ApiModelProperty(notes = "Title of the ticket.")
	private String title;
	@ApiModelProperty(notes = "Description of the ticket.")
	private String description;
	@ApiModelProperty(notes = "State of the ticket: 0 = UNACCEPTED, 1 = PENDING, 2 = ACCEPTED, 3 = CLOSED")
	private int state;
	@ApiModelProperty(notes = "The latitude coordinate of the ticket's location.")
	private double latitude;
	@ApiModelProperty(notes = "The longitude coordinate of the ticket's location.")
	private double longitude;
	@ApiModelProperty(notes = "The address of the ticket.")
	private String address;
	@ApiModelProperty(notes = "The customer's rating on the ticket.")
	private float rating;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	@JsonIgnore
	@ApiModelProperty(notes = "The actor that represents the customer of the ticket.")
	private Actor customerActor;
	
	@ManyToOne
	@JoinColumn(name="technician_id")
	@JsonIgnore
	@ApiModelProperty(notes = "The actor that represents the technician of the ticket.")
	private Actor technicianActor;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	@JsonIgnore
	@ApiModelProperty(notes = "The category of the ticket.")
	private Category categoryObj;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<ChatMessage> chatMessages;
	
	
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

	public List<ChatMessage> getMessages() {
		return chatMessages;
	}
	
	public void setMessages(List<ChatMessage> messages) {
		this.chatMessages = messages;
	}
	
	public void addMessage(ChatMessage message) {
		this.chatMessages.add(message);
	}
	
	public boolean isAssociated(int actor_id) {
		
		Actor technician = getTechnician();
		Actor customer = getCustomer();
		
		boolean valid = (technician != null && technician.getId() == actor_id) || (customer != null && customer.getId() == actor_id);
		
		return valid;
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
