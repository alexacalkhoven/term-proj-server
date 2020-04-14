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
public class CourseCatalogue implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private DBManager db;

	/**
	 * Initialize the course catalogue, and create test courses
	 */
	public CourseCatalogue(DBManager db) {
		this.db = db;
	}
	
	public int getCourseListLength() {
		// TODO: make this do a thing!
		return -3;
	}

	/**
	 * Gets a course with specified name and number
	 * @param courseName Course name
	 * @param courseNum Course number
	 * @return The course, or null if none found
	 */
	public Course getCourse(String courseName, int courseNum) {
		Course course = null;
		ResultSet res = db.query("SELECT * FROM courses WHERE name=? AND number=? LIMIT 1", courseName, courseNum);

		try {
			if (res.next()) {
				String name = res.getString(2);
				int number = res.getInt(3);
				
				course = new Course(name, number);
				course.setId(res.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return course;
	}

	/**
	 * Creates a new offering for a course
	 * @param course The course
	 * @param secNum The number for the section
	 * @param secCap The capacity for the section
	 * @return Whether the creation was successful or not
	 */
	public boolean createCourseOffering(Course course, int secNum, int secCap) {
		if (course == null) {
			System.err.println("Error, cannot create offering for null course");
			return false;
		}

		if (secNum <= 0) {
			System.err.println("Error, cannot create offering with section number < 0");
			return false;
		}

		if (secCap < 0) {
			System.err.println("Error, cannot create offering with section capacity < 0");
			return false;
		}

		if (course.getCourseOffering(secNum) != null) {
			System.err.println("Error, this section number already exists");
			return false;
		}

		CourseOffering offering = new CourseOffering(secNum, secCap);
		course.addOffering(offering);

		System.out.println("Created course offering for " + course.getFullName() + ":");
		System.out.println(offering);
		
		return true;
	}

	/**
	 * Creates a new course with given name and number
	 * @param name Course name
	 * @param num Course number
	 * @return Whether the course creation was successful or not
	 */
	public boolean createCourse(String name, int num) {
		Course course = getCourse(name, num);

		if (course != null) {
			System.err.println("Error, this course already exists: " + name + " " + num);
			return false;
		}

//		courseList.add(new Course(name, num));
		db.execute("INSERT INTO courses (name, number) VALUES (?, ?)", name, num);
		System.out.println("Created course: " + name + " " + num);
		return true;
	}
	
	/**
	 * Removes course with given name and number
	 * @param name Course name
	 * @param num Course number
	 * @return Whether the course removal was successful or not
	 */
	public boolean removeCourse(String name, int num) {
		Course course = getCourse(name, num);
		
		if (course == null)
			return false;

		System.out.println("Removed course: " + course.getName() + " " + course.getNumber());
		// TODO: make the databaseo do a deleto
		return true;
	}

	/**
	 * Gets all courses
	 * @return Returns list of all courses
	 */
	public ArrayList<Course> getCourses() {
		return null;
	}

	public String toString() {
		return "\nAll courses in the catalogue:\n";
	}
}
