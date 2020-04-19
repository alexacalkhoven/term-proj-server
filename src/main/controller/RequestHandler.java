package main.controller;

/**
 * Handles the requests
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public interface RequestHandler {
	public Response run(Object object) throws InvalidRequestException;
}