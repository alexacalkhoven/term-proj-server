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
	 * ?!?!?!?!?!??!?!?!?!?!?!?
	 * @author Jordan!!!!
	 * @param ?? Who knows, where's the function evevn???? we shall NEVER know!!
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
		db.execute("DELETE FROM students WHERE id=?", id);
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
		ResultSet res = db.query("SELECT * FROM students WHERE id=?", id);
		
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
	
	public void createRegistration(int studentId, int offeringId) {
		db.execute(
				"INSERT INTO registrations (student_id, offering_id, grade) VALUES (?, ?, ?)",
				studentId, offeringId, 'A');
	}
	
	public void removeRegistration(int studentId, int courseId) {
		db.execute("DELETE FROM registrations WHERE student_id=? AND course_id=?", studentId, courseId);
	}
	
	public ArrayList<Registration> getRegistrations(int studentId) {
		ArrayList<Registration> regs = new ArrayList<Registration>();
		ResultSet res = db.query(
				"SELECT registrations.id, registrations.grade, students.id, students.name, offerings.*, courses.name, courses.number "
				+ "FROM registrations "
				+ "INNER JOIN students ON registrations.student_id=students.id "
				+ "INNER JOIN offerings ON registrations.offering_id=offerings.id AND students.id=? "
				+ "INNER JOIN courses ON courses.id=offerings.course_id;", studentId);
		
		try {
			if (res.next()) {
				int regId = res.getInt(1);
				char grade = res.getString(2).charAt(0);
				
				String studentName = res.getString(4);
				Student student = new Student(studentName, studentId);
				
				int offeringId = res.getInt(5);
				int secNum = res.getInt(6);
				int secCap = res.getInt(7);
				int studentAmount = res.getInt(8);
				
				int courseId = res.getInt(9);
				String courseName = res.getString(10);
				int courseNumber = res.getInt(11);
				
				Course course = new Course(courseName, courseNumber);
				course.setCourseId(courseId);

				CourseOffering off = new CourseOffering(secNum, secCap);
				off.setOfferingId(offeringId);
				off.setStudentAmount(studentAmount);
				off.setCourse(course);
				
				Registration reg = new Registration();
				reg.setOffering(off);
				reg.setStudent(student);
				
				regs.add(reg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return regs;
	}
}

