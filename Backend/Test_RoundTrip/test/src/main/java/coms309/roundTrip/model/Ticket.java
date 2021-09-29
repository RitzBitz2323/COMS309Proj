package coms309.roundTrip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Tickets")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String name;
	private String[] tags;
	private int state;                // 0 = OPEN, 1 = PENDING, 2 = CLOSED
	private double[] location;
	private float rating;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Actor customerActor;
	
	@ManyToOne
	@JoinColumn(name="technician_id")
	private Actor technicianActor;
	
	@Column(name = "category")
	private String category;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
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
}
