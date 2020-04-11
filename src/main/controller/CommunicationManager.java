package main.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/*
 * An explanation of Radu's spicy code for Alexa's health and well being:
 * 
 * In CourseController and StudentController you call registerHandlerClass which fills the map in CommunicationManager 
 * with <String, RequestHandler> objects (by searching thru CourseController/StudentController you will find annotations; 
 * you will use the text that comprises the annotation as the String object in the map element, and the RequestHandler gets 
 * its run method defined using the code below the annotation). When handleRequest gets called, it searches the map 
 * for a String matching the command String sent, and it will call run on the associated RequestHandler. The run function
 * will already have been defined because it was setup during the registerHandlerClass function.
 */

/**
 * Stores and sets up the RequestHandlers, which can be accessed with a key (String) associated with the command.
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
	 * @param handler Creates an anon class where the RequestHandler is implemented and the run
	 * function is defined.
	 */
	public void registerHandler(String command, RequestHandler handler) {
		if (handlers.containsKey(command)) {
			System.err.println("Cannot register two handlers with the same name: " + command);
			return;
		}
		
		handlers.put(command, handler);
	}
	
	/**
	 * Runs the appropriate function in response to the Request and returns the Response.
	 * 
	 * @param req The request sent by the client.
	 * @return Response
	 */
	public Response handleRequest(Request req) {
		if (!handlers.containsKey(req.getCommand())) {
			System.err.println("Invalid command: " + req.getCommand());
			
			Response res = new Response(req.getCommand());
			res.setError("Command '" + req.getCommand() + "' does not exist");
			return res;
		}
		
		System.out.println("Command: " + req.getCommand());
		
		try {
			//gets the RequestHandler in the map associated with the command and runs it
			return handlers.get(req.getCommand()).run(req.getData());
		} catch (InvalidRequestException e) {
			System.err.println("Invalid Request '" + req.getCommand() + "': " + e.getMessage());
			
			Response res = new Response(req.getCommand());
			res.setError(e.getMessage());
			return res;
		}
	}
	
	/**
	 * Links the annotated functions of a class to an element of the map.
	 * 
	 * @param obj The class to be registered.
	 */
	public void registerHandlerClass(Object obj) {
		Class<?> cls = obj.getClass();
		
		for (Method method : cls.getMethods()) {
			//is there an annotation of type HandleRequest on this method?
			if (method.isAnnotationPresent(HandleRequest.class)) {
				//why make a HandleRequest object equal to an annotation?
				//HandleRequest is essentially the annotation class (therefore you can set them equal)
				// no i uhhh... no... yeah... i .... NO... wait... NO.. let that die one sec... no
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
					} catch (InvocationTargetException e) {
						System.err.println("Invalid request: " + req.value());
						Throwable targetEx = e.getTargetException();
						
						if (targetEx instanceof ClassCastException) {
							res.setError("Invalid request, incorrect arguments: " + targetEx.getMessage());
						} else {
							res.setError("Server error: " + targetEx.getClass() + " | " + targetEx.getMessage());
						}
						
						
					} catch (IllegalArgumentException | ClassCastException e) {
						System.err.println("Invalid request: " + req.value());
						res.setError("Invalid request, incorrect arguments: " + e.getMessage());
					} catch (Exception e) {
						System.err.println("Error running command: " + req.value());
						res.setError(e.getMessage());
						e.printStackTrace();
					}
					
					return res;
				});
			}
		}
	}
}
