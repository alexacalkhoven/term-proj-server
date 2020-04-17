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

	/**
	 * Gets a course with specified name and number
	 * @param courseName Course name
	 * @param courseNum Course number
	 * @return The course, or null if none found
	 */
	public Course getCourse(String courseName, int courseNum) {
		String targetName = courseName.toLowerCase();
		Course course = null;
		
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
		
		db.execute("INSERT INTO offerings (number, capacity, students, course_id) VALUES (?, ?, ?, ?, ?)",
				secNum, secCap, 0, course.getCourseId());

		System.out.println("Created course offering for " + course.getFullName() + ":");
		return true;
	}

	/**
	 * Creates a new course with given name and number
	 * @param name Course name
	 * @param num Course number
	 * @return Whether the course creation was successful or not
	 */
	public boolean createCourse(String name, int num) {
		db.execute("INSERT INTO courses (name, number) VALUES (?, ?)", name, num);
		return true;
	}
	
	/**
	 * Removes course with given name and number
	 * @param name Course name
	 * @param num Course number
	 * @return Whether the course removal was successful or not
	 */
	public boolean removeCourse(String name, int num) {
		db.execute("DELETE FROM courses WHERE name=? AND number=?", name, num);
		return true;
	}

	/**
	 * Gets all courses
	 * @return Returns list of all courses
	 */
	public ArrayList<Course> getCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		ResultSet res = db.query("SELECT * FROM courses");
		
		try {
			while (res.next()) {
				int id = res.getInt(1);
				String name = res.getString(2);
				int number = res.getInt(3);
				
				Course course = new Course(name, number);
				course.setCourseId(id);
				
				courses.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return courses;
	}
}
