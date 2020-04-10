package main.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/** 
 * Contains a Map that will redirect a Request key (String) to the proper function that will execute it.
 * 
 * @author Radu Schirliu
 */
public class CommunicationManager {
	Map<String, RequestHandler> handlers;
	
	public CommunicationManager() {
		handlers = new HashMap<String, RequestHandler>();
	}
	
	/**
	 * Creates new Map element for a new command.
	 * 
	 * @param command Command name.
	 * @param handler T
	 */
	public void registerHandler(String command, RequestHandler handler) {
		if (handlers.containsKey(command)) {
			System.err.println("Command requested already exists.");
			return;
		}
		
		handlers.put(command, handler);
	}
	
	public Object handleRequest(Request req) {
		if (!handlers.containsKey(req.getCommand())) {
			System.err.println("Error, key does not exist: " + req.getCommand());
			return null;
		}
		
		System.out.println("handling: " + req.getCommand());
		
		return handlers.get(req.getCommand()).run(req.getData());
	}
	
	public void registerHandlerClass(Object obj) {
		Class<?> cls = obj.getClass();
		
		for (Method method : cls.getMethods()) {
			if (method.isAnnotationPresent(HandleRequest.class)) {
				HandleRequest req = method.getAnnotation(HandleRequest.class);
				
				registerHandler(req.value(), (Object data) -> {
					Response res = new Response(req.value(), null);
					
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
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return res;
				});
			}
		}
	}
}
