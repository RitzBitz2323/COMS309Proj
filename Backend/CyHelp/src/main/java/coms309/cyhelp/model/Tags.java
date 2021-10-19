package coms309.cyhelp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name ="Tags")
public class Tags {
	
	//values of the Tags
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String tagName;
	
	@ManyToMany(mappedBy = "tag")
	private String category;
	
	
}
