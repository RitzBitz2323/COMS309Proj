package coms309.cyhelp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import coms309.cyhelp.model.Category;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name ="Tags")
public class Tags {
	
	//values of the Tags
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Identification Number of the Tag",name="id")
	private int id;
	
	@ApiModelProperty(notes = "Name of the tag",name="tagName")
	private String tagName;
	
	@ManyToMany(mappedBy = "tags")
	@ApiModelProperty(notes = "List of Categories",name="categories")
	private List<Category> categories;
	
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
	
	public String getTagName() {
		return this.tagName;
	}
	
	public void setTaName(String name) {
		this.tagName = name;
	}
	
	@Override
	public String toString() {
		return (this.getTagName() + ": id = " + this.getId());
	}

}
