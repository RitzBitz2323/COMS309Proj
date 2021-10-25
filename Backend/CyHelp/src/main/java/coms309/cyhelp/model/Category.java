package coms309.cyhelp.model;

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

@Entity
@Table(name ="Category")
public class Category {
	
	// values of the Category
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String title;
	
	@OneToMany
	@JsonIgnore
	private List<Ticket> tickets;
	
	@ManyToMany
	@JoinTable(name = "tags_for_category", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
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
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Ticket> getTickets() {
		return this.tickets;
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
	
	public List<Tags> getTags() {
		return this.tags;
	}
	
	public void setTags(List<Tags> tags) {
		this.tags = tags;
	}
	
	public void addTag(Tags tag) {
		this.tags.add(tag);
	}
	
	public void removeTag(Tags tag) {
		this.tags.remove(tag);
	}
	
	@Override
	public String toString() {
		return (this.getTitle() + ": id = " + this.getId());
	}
}
