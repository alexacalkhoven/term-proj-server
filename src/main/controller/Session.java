package main.controller;

import java.io.IOException;
import java.net.Socket;

public class Session implements Runnable {
	private Socket socket;
	private boolean running = false;
	private CourseCatalogue courseCatalogue;
	private StudentList studentList;
	
	public Session(Socket socket) {
		this.socket = socket;
		courseCatalogue = new CourseCatalogue();
		studentList = new StudentList();
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
