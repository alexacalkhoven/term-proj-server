package main;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import main.controller.DBManager;
import main.controller.Session;

/**
 * Initializes the server and waits for a connection from the client.
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class Server {

	// Class to execute the Runnable
	private ExecutorService es;
	// Where the server and client exchange information through
	private ServerSocket serverSocket;
	private DBManager db;

	public static void main(String[] args) {
		int port = 4200;

		try {
			if (args.length == 1) {
				port = Integer.parseInt(args[0]);
			}

			// Connects the server to the correct port
			Server server = new Server(port);
			server.listen();
		} catch (NumberFormatException e) {
			System.err.println("If supplying arguments port, port must be a number!");
		}
	}

	/**
	 * Runs a server on the specified port.
	 * 
	 * @param port the port number.
	 */
	public Server(int port) {
		db = new DBManager();

		try {
			serverSocket = new ServerSocket(port);
			// Creates new threads as needed
			es = Executors.newCachedThreadPool();
			System.out.println("Server started on port " + port);
		} catch (BindException e) {
			System.err.println("Failed to start server");
			System.err.println("Port " + port + " is already in use");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Failed to start server");
			System.exit(1);
			e.printStackTrace();
		}
	}

	/**
	 * Waits for a client to start running on the socket and will connect on a new
	 * thread when a client is detected.
	 */
	public void listen() {
		System.out.println("Listening for connections...");

		// Loops, waiting for clients to connect at all times
		while (true) {
			try {
				Socket client = serverSocket.accept();
				// Starts a new session on a new (or unused) thread
				es.execute(new Session(client, db));
			} catch (IOException e) {
				System.err.println("Failed to get client connection");
				e.printStackTrace();
			}
		}
	}
}
