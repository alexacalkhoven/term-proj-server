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
		
		if (url == null || user == null || password == null) {
			System.err.println("Environment variables for database not set");
			return;
		}
		
		try {
			connection = DriverManager.getConnection(url, user, password);
			createTables();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void createTables() {
		execute("CREATE TABLE IF NOT EXISTS students (\r\n" + 
				"    id INT NOT NULL PRIMARY KEY,\r\n" + 
				"    name VARCHAR(255) NOT NULL\r\n" + 
				");");
		
		execute("CREATE TABLE IF NOT EXISTS courses (\r\n" + 
				"    name VARCHAR(255) NOT NULL,\r\n" + 
				"    number INT NOT NULL\r\n" + 
				");");
		
		execute("CREATE TABLE IF NOT EXISTS prerequisites (\r\n" + 
				"    course_name VARCHAR(255) NOT NULL,\r\n" + 
				"    course_number INT NOT NULL\r\n" + 
				");");
		
		execute("CREATE TABLE IF NOT EXISTS registrations (\r\n" + 
				"    id INT NOT NULL PRIMARY KEY,\r\n" + 
				"    student_id INT NOT NULL,\r\n" + 
				"    offerind_id INT NOT NULL,\r\n" + 
				"    grade CHAR(1) NOT NULL\r\n" + 
				");");
		
		execute("CREATE TABLE IF NOT EXISTS offerings (\r\n" + 
				"    number INT NOT NULL,\r\n" + 
				"    capacity INT NOT NULL,\r\n" + 
				"    students INT NOT NULL,\r\n" + 
				"    course_name VARCHAR(255) NOT NULL,\r\n" + 
				"    course_number INT NOT NULL\r\n" + 
				");");
	}
	
	public void execute(String query, Object ...args) {
		try {
			PreparedStatement s = connection.prepareStatement(query);
			
			for (int i = 0; i < args.length; i++) {
				s.setObject(i + 1, args[i]);
			}
			
			s.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
