package coms309.cyhelp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name ="Category")
public class Category {
	
	// values of the Category
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Category Identification Number",name="id")
	private int id;
	@ApiModelProperty(notes = "Title of the category",name="title")
	private String title;
	
	@OneToMany
	@JsonIgnore
	@ApiModelProperty(notes = "List of tickets",name="tickets")
	private List<Ticket> tickets = new ArrayList<Ticket>();
	
	@ManyToMany
	@JoinTable(name = "tags_for_category", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	@ApiModelProperty(notes = "List of Tags within categories",name="tags")
	private List<Tags> tags;
	// Constructors
	
	public Category(){}
	
	public Category(String title) {
		this.title = title;
	}
	
	// Getters and Setters
	
	public int getId() {
		return this.id;
	}
	
	public int getTicketId(Ticket input) {
		
		int ticketId = 0;
		for(int i = 0; i < tickets.size(); i ++) {
			if(tickets.get(i) == input) {
				return i;
			}
		}
		return ticketId;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setLowercase() {
		this.title.toLowerCase();
	}
	
	@JsonIgnore
	public List<Ticket> getAllTickets() {
		return this.tickets;
	}
	
	public Ticket getTicket(int id) {
		return tickets.get(id);
	}
	
	public void setTickets(List<Ticket> list) {
		this.tickets = list;
	}
	
	public void addTicket(Ticket ticket) {
		this.tickets.add(ticket);
	}
	
	public void removeTicket(Ticket ticket) {
		this.tickets.remove(ticket);
	}
	
	@Override
	public String toString() {
		return (this.getTitle() + ": id = " + this.getId());
	}
}
