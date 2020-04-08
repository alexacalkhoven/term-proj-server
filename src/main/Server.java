package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import main.model.Session;

public class Server {
	private ExecutorService es;
	private ServerSocket serverSocket;
	
	public static void main(String[] args) {
		System.out.println("hello alonk");
		System.out.println("java 13 witch !!!!!!");
		System.out.println("mais pourqoui pas :(");
		
		Server server = new Server(4200);
		server.listen();
	}
	
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			es = Executors.newCachedThreadPool();
			System.out.println("Server started");
		} catch (IOException e) {
			System.err.println("Failed to start server");
			e.printStackTrace();
		}
	}
	
	public void listen() {
		System.out.println("Listening for connections...");
		
		while (true) {
			try {
				Socket client = serverSocket.accept();
				es.execute(new Session(client));
			} catch (IOException e) {
				System.err.println("Failed to get client connection");
				e.printStackTrace();
			}
		}
		
		
	}
}
