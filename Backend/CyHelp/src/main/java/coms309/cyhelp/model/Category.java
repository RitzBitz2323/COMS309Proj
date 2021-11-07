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
/**
* This is the Tags model class.
* @author Ritvik Ambekar, Brandon Schumacher
* 
*/
@Entity
@Table(name ="Category")
public class Category {
	
	// values of the Category
	/**
	 * Category ID
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Category Identification Number",name="id")
	private int id;
	
	/**
	 * Category Title
	 */
	@ApiModelProperty(notes = "Title of the category",name="title")
	private String title;
	
	/**
	 * List of Tickets related to Category
	 */
	@OneToMany
	@JsonIgnore
	@ApiModelProperty(notes = "List of tickets",name="tickets")
	private List<Ticket> tickets = new ArrayList<Ticket>();
	
	/**
	 * List of Tags
	 */
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
	
	/**
	 * Gets id of Category
	 * @return id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Gets id of specified Ticket
	 * @return id
	 */
	public int getTicketId(Ticket input) {
		
		int ticketId = 0;
		for(int i = 0; i < tickets.size(); i ++) {
			if(tickets.get(i) == input) {
				return i;
			}
		}
		return ticketId;
	}
	/**
	 * Sets id of category
	 * 
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Gets title of category
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Sets title of category
	 * @return id
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Sets category to lowercase
	 * 
	 */
	public void setLowercase() {
		this.title.toLowerCase();
	}
	
	/**
	 * Gets all tickets
	 * @return List of tickets
	 */
	@JsonIgnore
	public List<Ticket> getAllTickets() {
		return this.tickets;
	}
	
	/**
	 * Gets ticket from specified id
	 * @return ticket
	 */
	public Ticket getTicket(int id) {
		return tickets.get(id);
	}
	/**
	 * Sets ticket list with specified list of tickets
	 * 
	 */
	public void setTickets(List<Ticket> list) {
		this.tickets = list;
	}
	/**
	 * Adds ticket with specified created ticket as input
	 * 
	 */
	public void addTicket(Ticket ticket) {
		this.tickets.add(ticket);
	}
	/**
	 * Removes ticket with specified ticket as input
	 */
	public void removeTicket(Ticket ticket) {
		this.tickets.remove(ticket);
	}
	
	/**
	 * Outputs value of category as string
	 * @return String
	 */
	@Override
	public String toString() {
		return (this.getTitle() + ": id = " + this.getId());
	}
}
