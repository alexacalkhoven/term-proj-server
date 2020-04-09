package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import main.controller.Request;
import main.controller.Response;

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
	
	public void sendRequest(String command, Object data) {
		Request req = new Request(command, data);
		
		try {
			socketOut.writeObject(req);
			socketOut.reset();
			
			Response res = (Response)socketIn.readObject();
			System.out.println("Got response: " + res.getCommand() + " " + res.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendRequest(String command) {
		sendRequest(command, null);
	}

	public void communicate()  {
		// good strings to test with :))))
		// v cool
		ArrayList<String> strangs = new ArrayList<String>();
		strangs.add("alexa is");
		strangs.add("a");
		strangs.add("DINGUS!!!!");
		strangs.add(":(");
		
		try {
			Thread.sleep(400);
			
			sendRequest("with-args", strangs);
			sendRequest("wow");
			
			socketOut.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException  {
		TestClient tc = new TestClient("localhost", 4200);
	}
}
