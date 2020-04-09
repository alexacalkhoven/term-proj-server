package main.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseCatalogue implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Course> courseList;

	public CourseCatalogue() {
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

	public Course searchCourse(String courseName, int courseNum) {
		Course course = getCourse(courseName, courseNum);

		if (course == null) {
			System.err.println("Error, course not found: " + courseName + " " + courseNum);
		}

		return course;
	}

	public Course getCourse(String courseName, int courseNum) {
		for (Course c : courseList) {
			if (c.getName().contentEquals(courseName) && c.getNumber() == courseNum) {
				return c;
			}
		}

		return null;
	}

	public void createCourseOffering(Course c, int secNum, int secCap) {
		if (c == null) {
			System.err.println("Error, cannot create offering for null course");
			return;
		}

		if (secNum <= 0) {
			System.err.println("Error, cannot create offering with section number < 0");
			return;
		}

		if (secCap < 0) {
			System.err.println("Error, cannot create offering with section capacity < 0");
			return;
		}

		if (c.getCourseOffering(secNum) != null) {
			System.err.println("Error, this section number already exists");
			return;
		}

		CourseOffering offering = new CourseOffering(secNum, secCap);
		c.addOffering(offering);

		System.out.println("Created course offering for " + c.getFullName() + ":");
		System.out.println(offering);
	}

	public void createCourse(String name, int num) {
		Course course = getCourse(name, num);

		if (course != null) {
			System.err.println("Error, this course already exists");
			return;
		}

		courseList.add(new Course(name, num));
		System.out.println("Created course: " + name + " " + num);
	}

	public void removeCourse(Course course) {
		if (course == null)
			return;

		courseList.remove(course);
		System.out.println("Removed course: " + course.getName() + " " + course.getNumber());
	}

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
