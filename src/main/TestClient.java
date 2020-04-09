package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import main.controller.Request;

public class TestClient {
	private ObjectOutputStream socketOut;
	private Socket socket;
	private BufferedReader stdIn;
	private ObjectInputStream socketIn;

	public TestClient(String serverName, int portNumber) {
		try {
			// paleeendrum
			socket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new ObjectInputStream(socket.getInputStream());
			socketOut = new ObjectOutputStream(socket.getOutputStream());
			communicate();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	public void communicate()  {
		// good strings to test with :))))
		// v cool
		ArrayList<String> strangs = new ArrayList<String>();
		strangs.add("alexa is");
		strangs.add("a");
		strangs.add("DINGUS!!!!");
		strangs.add(":(");
		
		Request req = new Request("test", strangs);
		
		try {
			socketOut.writeObject(req);
			socketOut.reset();
			
			String str = (String)socketIn.readObject();
			System.out.println("got back: " + str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException  {
		TestClient tc = new TestClient("localhost", 4200);
	}
}
