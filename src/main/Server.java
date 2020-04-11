package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import main.controller.Session;

/**
 * Initializes the server and waits for a connection from the client.
 * 
 * @author Radu Schirliu
 */
public class Server {
	
    //Class to execute the Runnable
	private ExecutorService es;
    //Where the server and client exchange information through
	private ServerSocket serverSocket;
	
	public static void main(String[] args) {
        //Connects the server to the correct port
		Server server = new Server(4200);
		server.listen();
	}
	
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
            //Creates new threads as needed
			es = Executors.newCachedThreadPool();
			System.out.println("Server started");
		} catch (IOException e) {
			System.err.println("Failed to start server");
			System.exit(0);
			e.printStackTrace();
		}
	}
	
    /**
     * Waits for a client to start running on the socket and 
     * will connect on a new thread when a client is detected.
     */
	public void listen() {
		System.out.println("Listening for connections...");
		
        //Loops, waiting for clients to connect at all times
		while (true) {
			try {
				Socket client = serverSocket.accept();
                //Starts a new session on a new (or unused) thread
				es.execute(new Session(client));
			} catch (IOException e) {
				System.err.println("Failed to get client connection");
				e.printStackTrace();
			}
		}
	}
}
