package coms309.cyhelp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms309.cyhelp.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

		Ticket findById(int id);
}
