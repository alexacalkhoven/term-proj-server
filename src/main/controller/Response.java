package main.controller;

public class Response {
	public String command;
	public Object data;
	
	public Response(String command, Object data) {
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
