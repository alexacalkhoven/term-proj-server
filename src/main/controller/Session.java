package main.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Session implements Runnable {
	private Socket socket;
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	private boolean running = false;
	private CourseCatalogue courseCatalogue;
	private StudentList studentList;
	private DBManager db;
	
	public Session(Socket socket) {		
		this.socket = socket;
		courseCatalogue = new CourseCatalogue();
		studentList = new StudentList();
		db = new DBManager();
		
		try {
			socketIn = new ObjectInputStream(socket.getInputStream());
			socketOut = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Error, failed to create streams for socket I/O");
			e.printStackTrace();
		}
	}
	
	public void write(Object object) {
		try {
			socketOut.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
