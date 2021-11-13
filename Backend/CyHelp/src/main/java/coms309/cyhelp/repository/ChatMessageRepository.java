package coms309.cyhelp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import coms309.cyhelp.model.ChatMessage;
import coms309.cyhelp.model.Ticket;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
	
	ChatMessage findById(int id);
	List<ChatMessage> findAllByTicket(Ticket ticket);
	
}
