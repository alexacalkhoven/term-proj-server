package main.controller;

/**
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public interface RequestHandler {
	public Response run(Object object) throws InvalidRequestException;
}