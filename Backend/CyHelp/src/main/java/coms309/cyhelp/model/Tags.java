package coms309.cyhelp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import coms309.cyhelp.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name ="Tags")
public class Tags {
	
	//values of the Tags
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String tagName;
	
	@ManyToMany(mappedBy = "tags")
	private List<Tags> tags;
	
	public Tags() {
		
	}
	
	public Tags(String name) {
		this.tagName = name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTag() {
		return this.tagName;
	}
	
	public void setTag(String name) {
		this.tagName = name;
	}
	
	@Override
	public String toString() {
		return (this.getTag() + ": id = " + this.getId());
	}

}
