package main.controller;

import java.io.Serializable;

/**
 * Class of Request for making requests to the server
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class Request implements Serializable {
	private static final long serialVersionUID = 1L;

	private String command;
	private Object data;

	/**
	 * Constructor of Request
	 * 
	 * @param command the command
	 * @param data    the data
	 */
	public Request(String command, Object data) {
		this.command = command;
		this.data = data;
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
