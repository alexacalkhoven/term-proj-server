package main.model;

import java.net.Socket;

public class Session implements Runnable {
	private Socket socket;
	private boolean running = false;
	
	public Session(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		while (running) {
			
		}
	}
}
