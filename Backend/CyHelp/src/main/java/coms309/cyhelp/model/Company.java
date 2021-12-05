package coms309.cyhelp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name ="Companies")
public class Company {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@ApiModelProperty(notes = "The name of the Company")
	private String name;
	
	private String username;
	private long password;
	
	@ApiModelProperty(notes = "The id of the actor associated with this company.")
	private int user_id;
	
	public Company(String name, String username, long password) {
		
		this.name = name;
		this.username = username;
		this.password = password;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public long getPassword() {
		return this.password;
	}
	
	public void setUserId(int id) {
		this.user_id = id;
	}
	
	public int getUserId() {
		return this.user_id;
	}
}
