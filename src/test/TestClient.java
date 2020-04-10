package test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.controller.Request;
import main.controller.Response;

public class TestClient {
	private ObjectOutputStream socketOut;
	private Socket socket;
	private ObjectInputStream socketIn;

	public TestClient(String serverName, int portNumber) {
		try {
			socket = new Socket(serverName, portNumber);
			socketIn = new ObjectInputStream(socket.getInputStream());
			socketOut = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	public Object sendRequest(String command, Object data) {
		Request req = new Request(command, data);
		
		try {
			socketOut.writeObject(req);
			socketOut.reset();
			
			Response res = (Response)socketIn.readObject();
			
			if (res.getError() == null) {
				System.out.println("Got response: " + res.getCommand() + " " + res.getData());
			}
			
			return res.getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Object sendRequest(String command, Object... data) {
		return sendRequest(command, data);
	}
	
	public Object sendRequest(String command) {
		return sendRequest(command, (Object)null);
	}
	
	public void close() {
		try {
			socketOut.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
