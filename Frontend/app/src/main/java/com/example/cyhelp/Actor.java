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
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.actorType = actorType;
        this.actorID = actorID;
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


    /**
     * Returns true if the actor is a user else false.
     */
    public boolean actorIsUser() {
        if (actorType == 0) { return true; }
        return false;
    }

    /**
     * Returns true if actor is a technician else false.
     */
    public boolean actorIsTechnician() {
        if (actorType == 1) { return true;}
        return false;
    }

    /**
     * Returns true if actor is a company else false.
     */
    public boolean actorIsCompany() {
        if (actorType == 2) { return true; }
        return false;
    }

    /**
     * Returns true if actor is an Admin.
     */
    public boolean actorIsAdmin() {
        if (actorType == 3) { return true; }
        return false;
    }

    public void createTicket(int ticketId, String title, String description, String address) {
        Ticket ticket = new Ticket(ticketId, this, title, description, address);
    }

    public boolean acceptTicket(Ticket ticket) {

        if (ticket.getTicketState() == 1) {
            ticket.acceptTicket(this);
            return true;
        } else {
            return false;
        }


    }

    public boolean closeTicket(Ticket ticket) {
        if (ticket.getTicketState() == 2) {
            ticket.closeTicket();
            return true;
        } else {
            return false;
        }
    }

}
