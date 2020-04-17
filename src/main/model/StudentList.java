package main.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.controller.DBManager;

/**
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class StudentList implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private DBManager db;

	/**
	 * Initialize the student list, add test students
	 */
	public StudentList(DBManager db) {
		this.db = db;
	}
	
	/**
	 * Gets the amount of students
	 * @return The length of the student list
	 */

	/**
	 * Creates a new student with given name and id, and adds it to the list
	 * @param name Student name
	 * @param id Student id
	 * @return Whether it was successful or not
	 */
	public boolean addStudent(String name, int id) {
		Student student = getStudent(id);

		if (student != null) {
			System.err.println("Student with this ID already exists: ");
			System.err.println(student);

			return false;
		}

		db.execute("INSERT INTO students (name, id) VALUES (?, ?)", name, id);
		System.out.println("Created new student:\n" + student);
		return true;
	}

	/**
	 * Removes student from list with given ID
	 * @param id Student ID to remove
	 * @return Whether the remove was successful or not
	 */
	public boolean removeStudent(int id) {
		Student student = getStudent(id);
		if(student == null) {
			System.err.println("Student with ID: " + id + " does not exist.");
			return false;
		}
		db.execute("DELETE FROM students WHERE id =?", id);
		System.out.println("Deleted Student: " + id);
		return true;
	}

	/**
	 * Returns student from list with given ID
	 * @param id Student ID to search for
	 * @return Student with given ID, or null if none found
	 */
	public Student getStudent(int id) {
	Student student = null;
	ResultSet res = db.query("SELECT * FROM students WHERE id = ?", id);
	try {
		if(res.next()) {
			int number = res.getInt(1);
			String name = res.getString(2);
			
			student = new Student(name, number);
			
			
		}
	} catch(SQLException e) {
		e.printStackTrace();
	}
		return student;
	}
}

