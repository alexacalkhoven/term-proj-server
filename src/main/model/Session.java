package main.model;

import java.io.IOException;
import java.net.Socket;

import main.controller.CommunicationManager;
import main.controller.CourseCatalogue;

public class Session implements Runnable {
	private Socket socket;
	private boolean running = false;
	private CommunicationManager comManager;
	private CourseCatalogue courseCatalogue;
	
	public Session(Socket socket) {
		this.socket = socket;
		comManager = new CommunicationManager();
		courseCatalogue = new CourseCatalogue();
	}
	
	public void run() {
		System.out.println("New client session running");
		
		while (running) {
			
		}
		
		try {
			socket.close();
		} catch (IOException e) {
			System.err.println("Failed to close socket");
		}
		
		System.out.println("Client session ended");
	}
}
