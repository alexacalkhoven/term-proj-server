package main.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
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
			createTables();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void createTables() {
		execute("CREATE TABLE IF NOT EXISTS students (\r\n" + 
				"    id INT NOT NULL UNIQUE PRIMARY KEY,\r\n" + 
				"    name VARCHAR(255) NOT NULL\r\n" + 
				");");
		
		execute("CREATE TABLE IF NOT EXISTS courses (\r\n" + 
				"    id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,\r\n" + 
				"    name VARCHAR(255) NOT NULL,\r\n" + 
				"    number INT NOT NULL\r\n" + 
				");");
		
		execute("CREATE TABLE IF NOT EXISTS prerequisites (\r\n" + 
				"    parent_id INT NOT NULL," + 
				"    child_id INT NOT NULL," + 
				"    UNIQUE (parent_id, child_id)" + 
				");");
		
		execute("CREATE TABLE IF NOT EXISTS registrations (\r\n" + 
				"    id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,\r\n" + 
				"    student_id INT NOT NULL,\r\n" + 
				"    offering_id INT NOT NULL,\r\n" + 
				"    grade CHAR(1) NOT NULL,\r\n" + 
				"    UNIQUE (student_id, offering_id)\r\n" +
				");");
		
		execute("CREATE TABLE IF NOT EXISTS offerings (\r\n" +
				"    id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,\r\n" + 
				"    number INT NOT NULL,\r\n" + 
				"    capacity INT NOT NULL,\r\n" + 
				"    students INT NOT NULL,\r\n" + 
				"    course_id INT NOT NULL\r\n" + 
				");");
	}
	
	public void executeFile(String filePath) {
//		BufferedReader reader = new BufferedReader(new FileInputStream(getClass().getResourceAsStream("./init.sql")));
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
