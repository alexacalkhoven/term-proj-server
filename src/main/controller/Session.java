package main.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Session implements Runnable {
	private Socket socket;
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	private boolean running = true;
	private CourseController courseController;
	private StudentController studentController;
	private DBManager db;
	private CommunicationManager comManager;
	
	public Session(Socket socket) {
		this.socket = socket;
		db = new DBManager();
		comManager = new CommunicationManager();
		courseController = new CourseController(comManager);
		studentController = new StudentController(comManager);
		
		try {
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
	
	@Override
	public void run() {
		System.out.println("New client session running");
		
		try {
			socketIn = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("Error, failed to create streams for socket I/O");
			e.printStackTrace();
		}
		
		while (running) {
			try {
				Request request = (Request)socketIn.readObject();
				Object res = comManager.handleRequest(request);
				
				if (res != null) {
					socketOut.writeObject(res);
				}
			} catch (Exception e) {
				running = false;
				break;
			}
		}
		
		try {
			socket.close();
		} catch (IOException e) {
			System.err.println("Failed to close socket");
		}
		
		System.out.println("Client session ended");
	}
}
