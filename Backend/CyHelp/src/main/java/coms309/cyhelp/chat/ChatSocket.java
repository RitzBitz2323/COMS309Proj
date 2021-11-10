package coms309.cyhelp.chat;

import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Controller;

@Controller
@ServerEndpoint(value = "/chat/{user_id}")
public class ChatSocket {
	
}
