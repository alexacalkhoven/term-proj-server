package main.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CommunicationManager {
	Map<String, RequestHandler> handlers;
	
	public CommunicationManager() {
		handlers = new HashMap<String, RequestHandler>();
	}
	
	public void registerHandler(String command, RequestHandler handler) {
		if (handlers.containsKey(command)) {
			System.err.println("Cannot register two handlers with the same name: " + command);
			return;
		}
		
		handlers.put(command, handler);
	}
	
	public Response handleRequest(Request req) {
		if (!handlers.containsKey(req.getCommand())) {
			System.err.println("Invalid command: " + req.getCommand());
			
			Response res = new Response(req.getCommand());
			res.setError("Command '" + req.getCommand() + "' does not exist");
			return res;
		}
		
		System.out.println("Command: " + req.getCommand());
		
		try {
			return handlers.get(req.getCommand()).run(req.getData());
		} catch (InvalidRequestException e) {
			System.err.println("Invalid Request '" + req.getCommand() + "': " + e.getMessage());
			
			Response res = new Response(req.getCommand());
			res.setError(e.getMessage());
			return res;
		}
	}
	
	public void registerHandlerClass(Object obj) {
		Class<?> cls = obj.getClass();
		
		for (Method method : cls.getMethods()) {
			if (method.isAnnotationPresent(HandleRequest.class)) {
				HandleRequest req = method.getAnnotation(HandleRequest.class);
				
				registerHandler(req.value(), (Object data) -> {
					Response res = new Response(req.value());
					
					try {
						if (method.getParameterCount() > 0) {
							Class<?> param = method.getParameterTypes()[0];
							res.setData(method.invoke(obj, param.cast(data)));
						} else {
							res.setData(method.invoke(obj));
						}
						
						if (method.getReturnType().equals(Void.class)) {
							res.setData(null);
						}
					} catch (InvocationTargetException e) {
						System.err.println("Invalid request: " + req.value());
						res.setError("Invalid request: " + e.getTargetException().getMessage());
					} catch (IllegalArgumentException | ClassCastException e) {
						System.err.println("Invalid request: " + req.value());
						res.setError("Invalid request, wrong arguments");
					} catch (Exception e) {
						System.err.println("Error running command: " + req.value());
						res.setError(e.getMessage());
//						e.printStackTrace();
					}
					
					return res;
				});
			}
		}
	}
}
