package main.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
@SuppressWarnings("unused")
public class DBManager {
	private Connection connection;

	public DBManager() {
		String url = System.getenv("ENSF_DB_URL");
		String user = System.getenv("ENSF_DB_USER");
		String password = System.getenv("ENSF_DB_PASSWORD");
		
		if (url == null || user == null || password == null) {
			System.err.println("Environment variables for database not set");
			return;
		}
		
		try {
			connection = DriverManager.getConnection(url, user, password);
			executeFile("/init.sql");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void executeFile(String filePath) {
		String content = "";
		
		try {
			InputStream stream = getClass().getResourceAsStream(filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			
			while (reader.ready()) {
				content += reader.readLine();
			}
			
			reader.close();
			
			String[] parts = content.split(";");
			for (String s : parts) {
				execute(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int execute(String query, Object ...args) {
		try {
			PreparedStatement s = connection.prepareStatement(query);
			
			for (int i = 0; i < args.length; i++) {
				s.setObject(i + 1, args[i]);
			}
			
			s.execute();
			return s.getUpdateCount();
		} catch (SQLException e) {
//			e.printStackTrace();
		}
		
		return 0;
	}
	
	public ResultSet query(String query, Object ...args) {		
		try {
			PreparedStatement s = connection.prepareStatement(query);
			
			for (int i = 0; i < args.length; i++) {
				s.setObject(i + 1, args[i]);
			}
			
			ResultSet res = s.executeQuery();
			return res;
		} catch (SQLException e) {
//			e.printStackTrace();
		}
		
		return null;
	}
}
