package com.example.cyhelp;

public class Ticket {
    int ticketId;
    String title;
    String description;
    int state;
    String address;
    Actor user;
    Actor tech;

    //create ticket in custom state
    public Ticket (int ticketId, Actor user, Actor tech, String title, String description, int state, String address) {
        this.ticketId = ticketId;
        this.title = title;
        this.description = description;
        this.state = state;
        this.address = address;
        this.user = user;
        this.tech = tech;
    }

    //Create a new ticket
    public Ticket (int ticketId, Actor user, String title, String description, String address) {
        this.ticketId = ticketId;
        this.title = title;
        this.description = description;
        this.state = 1;
        this.address = address;
        this.user = user;
        this.tech = null;
    }



    public Actor getUser() { return user; }

    public Actor getTech() { return tech; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public int getTicketState() { return state; }

    public String getAddress() { return address; }

    public boolean ticketIsOpen() {
        return (state == 1);
    }

    public boolean ticketIsAccepted() {
        return (state == 2);
    }

    public boolean ticketIsClosed() {
        return (state ==3);
    }

    public void acceptTicket(Actor tech) {
        if (state == 1) {
            this.tech = tech;
            state = 2;
        }
    }

    public void closeTicket() {
        if (state == 2) {
            state = 3;
        }
    }

















}
