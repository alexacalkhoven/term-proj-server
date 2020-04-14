package main.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		
		// i
		// in fact
		// do _not_
		
		if (url == null || user == null || password == null) {
			System.err.println("wtf dude!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			return;
		}
		
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		execute("INSERT INTO test VALUES (?)", "memes");
	}
	
	public boolean execute(String query, Object ...args) {
		try {
			PreparedStatement s = connection.prepareStatement(query);
			
			for (int i = 0; i < args.length; i++) {
				s.setObject(i + 1, args[i]);
			}
			
			return s.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
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
			e.printStackTrace();
		}
		
		return null;
	}
}
