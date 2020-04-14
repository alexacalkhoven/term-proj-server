package main.controller;

import java.io.Serializable;

/**
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
	
	public Response(String command) {
		this(command, null);
	}
	
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
