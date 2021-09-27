package coms309.roundTrip.test.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Actors")
public class Actors {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
		
	private int id;
	private String first_name;
	private String last_name;
	private String password;
	private String user_type;
	private String ticket_ids;
	private int num_of_tickets;
	private int rating;
	
	
}
