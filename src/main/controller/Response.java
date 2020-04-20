package main.controller;

import java.io.Serializable;

/**
 * Response of the server based on given requests
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class Response implements Serializable {
	private static final long serialVersionUID = 1L;

	private String command;
	private String error;
	private Object data;

	/**
	 * Create a new response with given command, and null data
	 * @param command Command used to make the request
	 */
	public Response(String command) {
		this(command, null);
	}

	/**
	 * Creates a new response with given command and data
	 * @param command Command used to make the request
	 * @param data Data returned from the net request
	 */
	public Response(String command, Object data) {
		this.command = command;
		this.data = data;
		this.error = null;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
