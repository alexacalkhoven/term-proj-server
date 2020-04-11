package main.model;

import java.io.Serializable;
import java.util.ArrayList;

import main.controller.CommunicationManager;

public class CourseCatalogue implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Course> courseList;

	/**
	 * Initialize the course catalogue, and create test courses
	 */
	public CourseCatalogue() {
		courseList = new ArrayList<Course>();
		
		Course engg233 = new Course("ENGG", 233);
		engg233.addOffering(new CourseOffering(1, 100));
		engg233.addOffering(new CourseOffering(2, 50));
		courseList.add(engg233);

		Course ensf409 = new Course("ENSF", 409);
		ensf409.addOffering(new CourseOffering(1, 150));
		courseList.add(ensf409);

		Course phys259 = new Course("PHYS", 259);
		phys259.addOffering(new CourseOffering(1, 80));
		phys259.addOffering(new CourseOffering(2, 90));
		courseList.add(phys259);
	}
	
	public int getCourseListLength() {
		return courseList.size();
	}

	/**
	 * Gets a course with specified name and number
	 * @param courseName Course name
	 * @param courseNum Course number
	 * @return The course, or null if none found
	 */
	public Course getCourse(String courseName, int courseNum) {
		String targetName = courseName.toLowerCase();
		
		for (Course c : courseList) {
			String name = c.getName().toLowerCase();
			
			if (name.contentEquals(targetName) && c.getNumber() == courseNum) {
				return c;
			}
		}

		return null;
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

		courseList.add(new Course(name, num));
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
		return courseList.remove(course);
	}

	/**
	 * Gets all courses
	 * @return Returns list of all courses
	 */
	public ArrayList<Course> getCourses() {
		return courseList;
	}

	public String toString() {
		String s = "\nAll courses in the catalogue:\n";

		for (Course c : courseList) {
			s += c + "\n";
		}

		s += "----------------\n";

		return s;
	}
}
