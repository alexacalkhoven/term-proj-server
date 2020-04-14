package main.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private int number;

	public Course(String name, int num) {
		setName(name);
		setNumber(num);
	}

	public void addPreReq(Course course) {
		// TODO: make work
	}

	public void addOffering(CourseOffering offering) {
		if (offering == null)
			return;

		// TODO: alexa STOP yawning!
	}

	public CourseOffering getCourseOffering(int section) {
		// TODO: make work
		return null;
	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String toString() {
		return "Course: " + getName() + " " + getNumber() + "\nAll course sections:\n";
	}
}
