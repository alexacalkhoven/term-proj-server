package main.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.model.CourseCatalogue;
import main.model.StudentList;

/**
 * Manages receiving requests from the Client and sends them to the
 * communication manager to be handled.
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
@SuppressWarnings("unused")
public class Session implements Runnable {
	private Socket socket;
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	private boolean running = true;
	private CourseController courseController;
	private StudentController studentController;
	private DBManager db;
	private CommunicationManager comManager;
	private CourseCatalogue courseCatalogue;
	private StudentList studentList;

	/**
	 * Sets up the various in/out sockets for communicating with the client on this
	 * thread.
	 * 
	 * @param socket Socket to connect to
	 * @param db Database manager
	 */
	public Session(Socket socket, DBManager db) {
		this.socket = socket;
		this.db = db;
		comManager = new CommunicationManager();
		courseCatalogue = new CourseCatalogue(db);
		studentList = new StudentList(db);
		courseController = new CourseController(comManager, db, courseCatalogue);
		studentController = new StudentController(comManager, studentList, courseCatalogue);

		try {
			// Sets up a socket to send serialized objects through
			socketOut = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Error, failed to create streams for socket I/O");
			e.printStackTrace();
		}
	}

	/**
	 * Writes the object into the out stream for the client to receive.
	 * 
	 * @param object Object to be sent.
	 */
	public void write(Object object) {
		try {
			socketOut.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Receives and completes requests from the client.
	 */
	@Override
	public void run() {
		System.out.println("New client session running");

		try {
			socketIn = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("Error, failed to create streams for socket I/O");
			e.printStackTrace();
		}

		// Loops, waiting for Requests to be sent from the client.
		while (running) {
			try {
				Request request = (Request) socketIn.readObject();
				// Handles the request based on the instruction String and data sent
				Object res = comManager.handleRequest(request);

				if (res != null) {
					socketOut.writeObject(res);
					socketOut.reset();
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
