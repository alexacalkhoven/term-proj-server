package main.controller;

/**
 * Handles the network requests
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public interface RequestHandler {
	/**
	 * A method to be ran to handle each request
	 * 
	 * @param object The argument sent to the handler
	 * @return A response object for the request
	 * @throws InvalidRequestException An exception if anything goes wrong within the handler
	 */
	public Response run(Object object) throws InvalidRequestException;
}