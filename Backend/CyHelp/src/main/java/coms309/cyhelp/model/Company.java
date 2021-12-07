package coms309.cyhelp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name ="Company")
public class Company {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The id of the company.")
	private int id;
	
	@ApiModelProperty(notes = "The name of the Company.")
	private String name;
	
	@ApiModelProperty(notes = "The username of the company user.")
	private String username;
	
	@ApiModelProperty(notes = "The password of the company user.")
	private long password;
	
	@ApiModelProperty(notes = "The id of the actor associated with this company.")
	private int user_id;
	
	@OneToMany(mappedBy="company", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	@ApiModelProperty(notes = "The list of technicians that are employees of this company.")
	private List<Actor> companyEmployees = new ArrayList<Actor>();
	
	public Company() {}
	
	public Company(String name, String username, long password) {
		
		this.name = name;
		this.username = username;
		this.password = password;
	}
	
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
	
	public void addEmployees(Actor employee) {
		this.companyEmployees.add(employee);
	}
	
	public List<Actor> getEmployees() {
		return this.companyEmployees;
	}
	
	public void setEmployees(List<Actor> employees) {
		this.companyEmployees = employees;
	}
}
