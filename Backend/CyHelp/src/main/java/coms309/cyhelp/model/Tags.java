package coms309.cyhelp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
/**
* This is the Tags model class.
* @author Ritvik Ambekar, Brandon Schumacher
* 
*/
@Entity
@Table(name ="Tags")
public class Tags {
	
	//values of the Tags
	
	/**
	 * Tags ID
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Identification Number of the Tag",name="id")
	private int id;
	
	/**
	 * Tags Name
	 */
	@ApiModelProperty(notes = "Name of the tag",name="tagName")
	private String tagName;
	
	/**
	 * List of Categories
	 */
	@ManyToMany(mappedBy = "tags")
	@ApiModelProperty(notes = "List of Categories",name="categories")
	private List<Category> categories;
	
	public Tags() {
		
	}
	
	public Tags(String name) {
		this.tagName = name;
	}
	
	/**
	 * Gets id of tag
	 * @return id
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * Sets id of tag
	 * 
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Gets name of tag
	 * @return tagName
	 */
	public String getTagName() {
		return this.tagName;
	}
	
	/**
	 * Sets name of tag
	 */
	public void setTagName(String name) {
		this.tagName = name;
	}
	
	/**
	 * Outputs tag attributes as string
	 * @return string 
	 */
	@Override
	public String toString() {
		return (this.getTagName() + ": id = " + this.getId());
	}

}
