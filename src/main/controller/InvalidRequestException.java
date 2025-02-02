package main.controller;

/**
 * class for InvalidRequestException
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class InvalidRequestException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Initialize the exception
	 * @param message Exception message
	 */
	public InvalidRequestException(String message) {
		super(message);
	}
}
