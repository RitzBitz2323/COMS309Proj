package coms309.cyhelp.chat;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import coms309.cyhelp.model.Actor;
import coms309.cyhelp.model.ChatMessage;
import coms309.cyhelp.model.Ticket;
import coms309.cyhelp.repository.ActorRepository;
import coms309.cyhelp.repository.ChatMessageRepository;
import coms309.cyhelp.repository.TicketRepository;

@Controller
@ServerEndpoint(value = "/chat/{ticket_id}/{actor_id}")
public class ChatSocket {
	
	private static Map<Session, Integer[]> sessionActorMap = new Hashtable<>();
	private static Map<Integer, Session> actorSessionMap = new Hashtable<>();

	private static final int NO_TICKET = 0;
	private final Logger logger = LoggerFactory.getLogger(ChatSocket.class);
	
	private static TicketRepository ticketRepository;
	@Autowired
	public void setTicketRepository(TicketRepository repo) {
		ticketRepository = repo;
	}
	
	private static ChatMessageRepository chatRepository;
	@Autowired
	public void setChatRepository(ChatMessageRepository repo) {
		chatRepository = repo;
	}
	
	private static ActorRepository actorRepository;
	@Autowired
	public void setActorRepository(ActorRepository repo) {
		actorRepository = repo;
	}
	

	@OnOpen
	public void onOpen(Session session, @PathParam("ticket_id") int ticketID, @PathParam("actor_id") int actorID) 
      throws IOException {

		logger.info("Entered into Open");
		
		Ticket ticket = ticketRepository.findById(ticketID);
		
		actorSessionMap.put(actorID, session);
		
		if(ticket != null) {
			if(ticket.isAssociated(actorID)) {
				// store the actorID and ticketID 
				logger.info("This Actor is associated with this ticket.");
				sessionActorMap.put(session, new Integer[] {actorID, ticketID});
				session.getBasicRemote().sendText(getChatHistory(ticket));
				//session.getBasicRemote().sendText("You have connected via ticket " + ticketID);
			} else {
				// access denied
				logger.info("Failed, actor is not associated with this ticket.");
				sessionActorMap.put(session, new Integer[] {actorID, 0});
				session.getBasicRemote().sendText("This actor doesn't have access to this ticket.");
			}
		} else {
			// non-existing ticket
			sessionActorMap.put(session, new Integer[] {actorID, 0});
			session.getBasicRemote().sendText("This ticket doesn't exist.");
		}
	}


	@OnMessage
	public void onMessage(Session session, String message) throws IOException {

		// Handle new messages
		logger.info("Entered into Message: Got Message:" + message);
		
		Integer[] actorInfo = sessionActorMap.get(session);
		
		sendMessageToTicket(actorInfo[0], actorInfo[1], message);
		
	}


	@OnClose
	public void onClose(Session session) throws IOException {
		
		logger.info("Entered into Close");

		// remove the user connection information
		int actorID = sessionActorMap.get(session)[0];
		sessionActorMap.remove(session);
		actorSessionMap.remove(actorID);
	}


	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error");
		throwable.printStackTrace();
	}
	
	private void sendMessageToTicket(int actor_id, int ticket_id, String message) {
		
		try {
			
			Actor actor = actorRepository.findById(actor_id);
			
			if(ticket_id != NO_TICKET) {
				
				Ticket ticket = ticketRepository.findById(ticket_id);
				int customerID = (ticket.getCustomer() == null) ? 0 : ticket.getCustomer().getId();
				int technicianID = (ticket.getTechnician() == null) ? 0 : ticket.getTechnician().getId();
				Session customerSession = actorSessionMap.get(customerID);
				Session technicianSession = actorSessionMap.get(technicianID);
				
				String text = "<" + actor.getUsername() + "> " + message;
				
				// send the message to the customer (if connected)
				if(customerSession != null)
					customerSession.getBasicRemote().sendText(text);
				// send the message to the technician (if connected)
				if(technicianSession != null)
					technicianSession.getBasicRemote().sendText(text);
				
				// Saving chat history to repository
				ChatMessage newMessage = new ChatMessage(actor_id, ticket, text);
				ticket.addMessage(newMessage);
				chatRepository.save(newMessage);
				ticketRepository.save(ticket);
				
			} else {
				
				logger.info("This actor does not have access to this ticket.");
			}
			
		} catch(Exception e) {
			
			//logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
		
	}

	// Gets the Chat history from the repository
	private String getChatHistory(Ticket ticket) {
		
		List<ChatMessage> messages = chatRepository.findAllByTicket(ticket);
    
		// convert the list to a string
		StringBuilder sb = new StringBuilder();
		if(messages != null && messages.size() != 0) {
			for (ChatMessage message : messages) {
				sb.append("\n" + message.getMessage());
			}
		}
		return sb.toString();
	}

}