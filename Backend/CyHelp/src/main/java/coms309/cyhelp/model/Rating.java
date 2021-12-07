package coms309.cyhelp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * This is the Ratings model class
 * 
 * */
@Entity
@Table(name="Ratings")
public class Rating {
	
	/** Id associated with USER**/
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(notes = "Identification Number of the Rating to associate with user",name="userid")
	private int userid;
	
	@OneToOne(mappedBy = "ratings")
    private Actor actor;
	/**
	 * Rating number
	 */
	@ApiModelProperty(notes = "Rating number out of 5" ,name="rating")
	private double ratingNum;
	
	public Rating() {}
	
	public Rating(int userId) {
		this.userid = userId;
		ratingNum = 5.0;
	}
	
	/* Getters and Setters */
	
	/**
	 * Gets userId of rating
	 * @return userid
	 */
	public int getId() {
		return this.userid;
	}
	/**
	 * Sets userId of rating
	 * 
	 */
	public void setId(int id) {
		this.userid = id;
	}
	
	/**
	 * Gets rating
	 * @return tagName
	 */
	public double getRating() {
		return this.ratingNum;
	}
	
	/**
	 * Sets rating to specifc number
	 */
	public void setRating(double rating) {
		this.ratingNum = rating;
	}
	
	/**
	 * Updates rating with inputted double
	 */
	public void updateRating(double number) {
		this.ratingNum = (this.ratingNum + number) /2;
	}
	
	/**
	 * Outputs rating attributes as string
	 * @return string 
	 */
	@Override
	public String toString() {
		return (this.getId() + ": id = " + this.getRating());
	}
	
}
