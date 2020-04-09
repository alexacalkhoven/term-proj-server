package main.controller;

import java.util.HashMap;
import java.util.Map;

public class CommunicationManager {
	Map<String, RequestHandler> handlers;
	
	public CommunicationManager() {
		handlers = new HashMap<String, RequestHandler>();
	}
	
	public void registerHandler(String command, RequestHandler handler) {
		if (handlers.containsKey(command)) {
			System.err.println("No.");
			return;
		}
		
		handlers.put(command, handler);
	}
	
	public Object handleRequest(Request req) {
		if (!handlers.containsKey(req.getCommand())) {
			System.err.println("Error, key does not exist: " + req.getCommand());
			return null;
		}
		
		return handlers.get(req.getCommand()).run(req.getData());
	}
}
