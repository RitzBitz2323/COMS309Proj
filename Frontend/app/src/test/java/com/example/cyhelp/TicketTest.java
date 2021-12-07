package com.example.cyhelp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class TicketTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    // Author - Nick Sandeen
    @Test
    public void getTicketStateTicketOpened() {
        Actor user = new Actor("nsUser", "Nick", "Sandeen", "Ames", 0, 10);
        Actor tech = new Actor("nsTech", "Bob", "Bobberson", "Ames", 1, 12);
        Ticket testTicket = mock(Ticket.class);

        user.createTicket(1, "Car Broke", "Its broke really bad", "Ames");

        when(testTicket.getTicketState()).thenReturn(1);

        Assert.assertEquals(true, tech.acceptTicket(testTicket));
    }

    // Author - Nick Sandeen
    @Test
    public void getTicketStateTicketAccepted() {
        Actor user = new Actor("nsUser", "Nick", "Sandeen", "Ames", 0, 10);
        Actor tech = new Actor("nsTech", "Bob", "Bobberson", "Ames", 1, 12);
        Actor tech2 = new Actor("nsTech2", "Bobby", "Bobber", "Ames", 1, 15);
        Ticket testTicket = mock(Ticket.class);

        user.createTicket(1, "Car Broke", "Its broke really bad", "Ames");

        when(testTicket.getTicketState()).thenReturn(1);
        tech.acceptTicket(testTicket);
        when(testTicket.getTicketState()).thenReturn(2);
        Assert.assertEquals(false, tech2.acceptTicket(testTicket));
    }

    // Author - Parthiv Ganguly
    @Test
    public void getTicketStateTicketClosed() {
        Actor user = new Actor("pUser", "Parthiv", "Ganguly", "ISU", 0, 10);
        Actor tech = new Actor("pTech", "Parthiv", "Ganguly", "ISU", 1, 12);
        Actor tech2 = new Actor("pTech2", "Parthiv", "Ganguly", "ISU", 1, 15);
        Ticket testTicket = mock(Ticket.class);

        user.createTicket(1, "Car Broke", "Its broke really bad", "Ames");

        when(testTicket.getTicketState()).thenReturn(1);
        tech.acceptTicket(testTicket);
        when(testTicket.getTicketState()).thenReturn(2);
        tech.closeTicket(testTicket);
        when(testTicket.getTicketState()).thenReturn(3);
        Assert.assertEquals(false, tech2.acceptTicket(testTicket));
        Assert.assertEquals(false, tech2.closeTicket(testTicket));
    }

    // Author - Parthiv Ganguly
    @Test
    public void getTicketDetails() {
        Actor user = new Actor("pUser", "Parthiv", "Ganguly", "ISU", 0, 10);
        Actor tech = new Actor("pTech", "Parthiv", "Ganguly", "ISU", 1, 12);
        Ticket testTicket = mock(Ticket.class);

        user.createTicket(1, "PC keeps crashing", "Processor keeps overheating", "Freddy Court");

        when(testTicket.getTitle()).thenReturn("PC keeps crashing");
        when(testTicket.getDescription()).thenReturn("Processor keeps overheating");
        when(testTicket.getAddress()).thenReturn("Freddy Court");
    }


}
