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
		
		ResultSet res = db.query("SELECT * FROM courses WHERE name=? AND number=?", targetName, courseNum);
		
		try {
			if (res.next()) {
				int id = res.getInt(1);
				String name = res.getString(2);
				int number = res.getInt(3);
				
				course = new Course(name, number);
				course.setCourseId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return course;
	}
	
	/**
	 * Gets a course with specified ID
	 * @param courseId Course ID to search for
	 * @return The course, or null if none found
	 */
	public Course getCourse(int courseId) {
		ResultSet res = db.query("SELECT * FROM courses WHERE id=?", courseId);
		Course course = null;
		
		try {
			if (res.next()) {
				String name = res.getString(2);
				int number = res.getInt(3);
				
				course = new Course(name, number);
				course.setCourseId(courseId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return course;
	}

	/**
	 * Creates a new offering for a course
	 * @param courseId The course ID
	 * @param secNum The number for the section
	 * @param secCap The capacity for the section
	 * @return Whether the creation was successful or not
	 */
	public boolean createCourseOffering(int courseId, int secNum, int secCap) {
		if (secNum <= 0) {
			System.err.println("Error, cannot create offering with section number < 0");
			return false;
		}

		if (secCap < 0) {
			System.err.println("Error, cannot create offering with section capacity < 0");
			return false;
		}
		
		int count = db.execute("INSERT INTO offerings (number, capacity, students, course_id) VALUES (?, ?, ?, ?)",
				secNum, secCap, 0, courseId);

		System.out.println("Created course offering for " + courseId);
		return count != 0;
	}
	
	/**
	 * Removes a course offering with a given ID
	 * @param offeringId Course offering to remove
	 * @return If it was successful or not
	 */
	public boolean removeCourseOffering(int offeringId) {
		int count = db.execute("DELETE FROM offerings WHERE id=?", offeringId);
		return count != 0;
	}

	/**
	 * Creates a new course with given name and number
	 * @param name Course name
	 * @param num Course number
	 * @return Whether the course creation was successful or not
	 */
	public boolean createCourse(String name, int num) {
		int count = db.execute("INSERT INTO courses (name, number) VALUES (?, ?)", name, num);
		return count != 0;
	}
	
	/**
	 * Removes course with given course ID
	 * @param courseId Course ID to remove
	 * @return Whether the course removal was successful or not
	 */
	public boolean removeCourse(int courseId) {
		int count = db.execute("DELETE FROM courses WHERE id=?", courseId);
		return count != 0;
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
	
	/**
	 * Gets all the offerings for a given course ID
	 * @param courseId Course ID to search for
	 * @return All offerings for course with given ID
	 */
	public ArrayList<CourseOffering> getOfferings(int courseId) {
		ArrayList<CourseOffering> offerings = new ArrayList<CourseOffering>();
		ResultSet res = db.query("SELECT offerings.id, offerings.number, offerings.capacity, offerings.students, courses.* FROM "
				+ "offerings INNER JOIN courses ON offerings.course_id=courses.id AND course_id=?;", courseId);

		try {
			while (res.next()) {
				int offeringId = res.getInt(1);
				int secNum = res.getInt(2);
				int secCap = res.getInt(3);
				int studentAmount = res.getInt(4);

				CourseOffering off = new CourseOffering(secNum, secCap);
				off.setOfferingId(offeringId);
				off.setStudentAmount(studentAmount);
				
				String courseName = res.getString(6);
				int courseNumber = res.getInt(7);
				
				Course course = new Course(courseName, courseNumber);
				course.setCourseId(courseId);
				
				off.setCourse(course);				
				offerings.add(off);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return offerings;
	}
	
	/**
	 * Gets preqres for course with given ID
	 * @param courseId The course ID to get prereqs for
	 * @return Prereqs
	 */
	public ArrayList<Course> getPreReqs(int courseId) {
		ArrayList<Course> preReqs = new ArrayList<Course>();
		ResultSet res = db.query("SELECT courses.id, courses.name, courses.number FROM prerequisites "
				+ "INNER JOIN courses ON prerequisites.child_id=courses.id AND prerequisites.parent_id=?", courseId);
		
		try {
			while (res.next()) {
				int id = res.getInt(1);
				String name = res.getString(2);
				int number = res.getInt(3);

				Course course = new Course(name, number);
				course.setCourseId(id);
				preReqs.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return preReqs;
	}
	
	/**
	 * Adds a new prereq to a course
	 * @param parentCourseId Course to add prereq to
	 * @param childCourseId Course that is prereq to parentCourse
	 * @return If it was successful
	 */
	public boolean addPreReq(int parentCourseId, int childCourseId) {
		int count = db.execute("INSERT INTO prerequisites VALUES (?, ?)", parentCourseId, childCourseId);
		return count != 0;
	}
	
	/**
	 * Remove prereq from a course
	 * @param parentCourseId Course to remove prereq from
	 * @param childCourseId Course that is prereq to parentCourse
	 * @return If it was successful
	 */
	public boolean removePreReq(int parentCourseId, int childCourseId) {
		int count = db.execute("DELETE FROM prerequisites VALUES (?, ?)", parentCourseId, childCourseId);
		return count != 0;
	}
	
	/**
	 * Gets an offering with given ID
	 * @param offeringId ID to search for
	 * @return
	 */
	public CourseOffering getOffering(int offeringId) {
		CourseOffering offering = null;
		ResultSet res = db.query("SELECT * FROM offerings INNER JOIN courses ON offerings.course_id=courses.id AND offerings.id=?", offeringId);
		
		try {
			if (res.next()) {
				int secNum = res.getInt(2);
				int secCap = res.getInt(3);
				int studentAmount = res.getInt(4);

				offering = new CourseOffering(secNum, secCap);
				offering.setOfferingId(offeringId);
				offering.setStudentAmount(studentAmount);
				
				int courseId = res.getInt(6);
				String courseName = res.getString(7);
				int courseNumber = res.getInt(8);
				
				Course course = new Course(courseName, courseNumber);
				course.setCourseId(courseId);
				
				offering.setCourse(course);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return offering;
	}
}
