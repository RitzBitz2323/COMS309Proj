package com.example.cyhelp;


/**
 * This class stores info about the actor and allows for activities to easily pass actor info between other activities.
 *
 * @author Nick Sandeen
 */
public class Actor {

    String username;
    String firstName;
    String lastName;
    String address;
    int actorType;
    int actorID;


    /**
     * Actor object constructor
     * @param username: String username
     * @param firstName: String user's first name
     * @param lastName: String user's last name
     * @param address: String user home address
     * @param actorType: int actor type (0-user, 1-technician, 2-company, 3-admin)
     * @param actorID
     */
    public Actor (String username, String firstName, String lastName, String address, int actorType, int actorID) {

    }

    /**
     * Return the actor's username
     *
     * @return String username
     */
    public String getUsername() {return username;}

    /**
     * Returns the actor's first name
     *
     * @return String firstName
     */
    public String getFirstName() {return firstName;}

    /**
     * Returns the actor's last name
     *
     * @return String lastName
     */
    public String getLastName() {return lastName;}

    /**
     * Returns the actor's home address
     *
     * @return String address
     */
    public String getAddress() {return address;}

    /**
     * Returns the actor type
     *
     * (0-user, 1-technician, 2-company, 3-admin)
     *
     * @return int actorType
     */
    public int getActorType() {return actorType;}

    /**
     * Returns the actor ID
     *
     * @return actorID
     */
    public int getActorID() {return actorID;}

}
