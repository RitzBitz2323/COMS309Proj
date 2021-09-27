package coms309.roundTrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms309.roundTrip.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

		Ticket findById(int id);
}
