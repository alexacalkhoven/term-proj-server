package main.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.controller.DBManager;

/**
 * A list of students
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
	 * Gets all students
	 * @return A list of all students
	 */
	public ArrayList<Student> getStudents() {
		ArrayList<Student> students = new ArrayList<Student>();
		ResultSet res = db.query("SELECT * FROM students");
		
		try {
			while (res.next()) {
				int number = res.getInt(1);
				String name = res.getString(2);
				
				Student student = new Student(name, number);
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return students;
	}

	/**
	 * Creates a new student with given name and id, and adds it to the list
	 * @param name Student name
	 * @param id Student id
	 * @return Whether it was successful or not
	 */
	public boolean addStudent(String name, int id) {
		int count = db.execute("INSERT INTO students (name, id) VALUES (?, ?)", name, id);
		return count != 0;
	}

	/**
	 * Removes student from list with given ID
	 * @param id Student ID to remove
	 * @return Whether the remove was successful or not
	 */
	public boolean removeStudent(int id) {
		db.execute("DELETE FROM registrations WHERE student_id=?", id);
		
		int count = db.execute("DELETE FROM students WHERE id=?", id);
		return count != 0;
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
	
	/**
	 * Creates a new registration
	 * @param studentId Student ID to create registration for
	 * @param offeringId Offering ID to create registration for
	 * @return Whether the creation was successful or not
	 */
	public boolean createRegistration(int studentId, int offeringId) {
		int count = db.execute(
				"INSERT INTO registrations (student_id, offering_id, grade) VALUES (?, ?, ?)",
				studentId, offeringId, "A");
		
		return count != 0;
	}
	
	/**
	 * Removes registration
	 * @param studentId Student ID to remove registration for
	 * @param offeringId Offering ID to remove registration for
	 * @return Whether the removal was successful or not
	 */
	public boolean removeRegistration(int studentId, int offeringId) {
		int count = db.execute("DELETE FROM registrations WHERE student_id=? AND offering_id=?", studentId, offeringId);
		return count != 0;
	}
	
	/**
	 * Gets all registrations for student with given ID
	 * @param studentId Student to get registrations for
	 * @return All registrations for student
	 */
	public ArrayList<Registration> getRegistrations(int studentId) {
		ArrayList<Registration> regs = new ArrayList<Registration>();
		ResultSet res = db.query(
				"SELECT registrations.id, registrations.grade, students.id, students.name, offerings.*, courses.name, courses.number "
				+ "FROM registrations "
				+ "INNER JOIN students ON registrations.student_id=students.id AND students.id=? "
				+ "INNER JOIN offerings ON registrations.offering_id=offerings.id "
				+ "INNER JOIN courses ON offerings.course_id=courses.id;", studentId);
		
		try {
			while (res.next()) {
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
				reg.setRegistrationId(regId);
				reg.setGrade(grade);
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

