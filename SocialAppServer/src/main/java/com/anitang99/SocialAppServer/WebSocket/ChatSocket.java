package com.anitang99.SocialAppServer.WebSocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/chat/{id}", configurator = CustomConfigurator.class)
@Component
public class ChatSocket {
	
	// Store all socket session and their corresponding username
//	private static Map<String, Session> sessionMap = new HashMap<>();
	
	// Store all socket session with their corresponding chatIds
	private static Map<String, HashSet<Session>> roomToSessionMap = new HashMap<>();
	private static Map<Session, String> sessionToRoomMap = new HashMap<>();
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String CONNECTED_MESSAGE = "connected"; 
	
	@OnOpen
    public void onOpen(Session session, @PathParam("id") String id) throws IOException {
        // Get session and WebSocket connection
		if(!roomToSessionMap.containsKey(id)) {
			roomToSessionMap.put(id, new HashSet<Session>());
		}
		roomToSessionMap.get(id).add(session);
		sessionToRoomMap.put(session, id);
		session.getUserProperties().put("username", session.getRequestParameterMap().get("username").get(0));
		session.getUserProperties().put("chatID", id);
		session.getAsyncRemote().sendText(CONNECTED_MESSAGE);
		logger.info(session.getUserProperties().get("username") + " has connected");
    }
 
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        // Handle new messages
    	logger.info("Message received from: " + session.getUserProperties().get("username"));    		
    	String username = (String) session.getUserProperties().get("username");
    	sendToRoom(sessionToRoomMap.get(session), username, message);
    }
 
    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
    	logger.info(session.getUserProperties().get("username") + " has disconnected");
    	roomToSessionMap.get(session.getUserProperties().get("chatID")).remove(session);
    	sessionToRoomMap.remove(session);
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    	throwable.printStackTrace();
    	try {
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    private void sendMessage(Session session, String message) {
    	logger.info("sendMessage entered");
    	session.getAsyncRemote().sendText(message);
    }
    
    private void sendToRoom(String id, String sender,String message) {
    	for (Session session : roomToSessionMap.get(id)) {
    		String username = (String) session.getUserProperties().get("username");
    		if(!username.equals(sender))
    			sendMessage(session, message);
		}
    }

}
