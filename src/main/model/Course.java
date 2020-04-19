package main.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class of a course holding the name, course id, course number, prerequisites and offerings.
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int courseId;
	private String name;
	private int number;
	private ArrayList<Course> preReq;
	private ArrayList<CourseOffering> offeringList;

	public Course(String name, int num) {
		setName(name);
		setNumber(num);

		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
	}
	
	public int getCourseId() {
		return courseId;
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	/**
	 *
	 * @return returns the length of the offeringList
	 */
	public int getOfferingListLength() {
		return offeringList.size();
	}
	/**
	 * Adds a prerequisite course
	 * @param course the course to be added
	 */
	public void addPreReq(Course course) {
		preReq.add(course);
	}
	/**
	 * Adds an offering
	 * @param offering the offering to be added
	 */
	public void addOffering(CourseOffering offering) {
		if (offering == null)
			return;

		offering.setCourse(this);
		offeringList.add(offering);
	}
	/**
	 * gets a course offering from the offeringList
	 * @param section the section number of the offering.
	 * @return returns the target CourseOffering
	 */
	public CourseOffering getCourseOffering(int section) {
		for (CourseOffering o : offeringList) {
			if (o.getSecNum() == section)
				return o;
		}

		return null;
	}
	/**
	 * searches the offeringList for a desired CourseOffering
	 * @param section the section number of the CourseOffering
	 * @return returns the target CourseOffering
	 */
	public CourseOffering searchOffering(int section) {
		CourseOffering offering = getCourseOffering(section);

		if (offering == null) {
			System.err.println("Error, section not found: " + section);

		}

		return offering;
	}

	public String getName() {
		return name;
	}

	public void setName(String courseName) {
		this.name = courseName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int courseNum) {
		this.number = courseNum;
	}

	public String getFullName() {
		return getName() + " " + getNumber();
	}

	public String toString() {
		String s = "Course: " + getName() + " " + getNumber() + "\nAll course sections:\n";

		for (CourseOffering c : offeringList) {
			s += c + "\n";
		}

		return s;
	}
}
