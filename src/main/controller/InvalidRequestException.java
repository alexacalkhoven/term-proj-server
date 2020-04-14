package main.controller;

/**
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class InvalidRequestException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public InvalidRequestException(String message) {
		super(message);
	}
}
