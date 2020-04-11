package main.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
	
	public int getOfferingListLength() {
		return offeringList.size();
	}

	public void addPreReq(Course course) {
		preReq.add(course);
	}

	public void addOffering(CourseOffering offering) {
		if (offering == null)
			return;

		offering.setCourse(this);
		offeringList.add(offering);
	}

	public CourseOffering getCourseOffering(int section) {
		for (CourseOffering o : offeringList) {
			if (o.getSecNum() == section)
				return o;
		}

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

	public String toString() {
		String s = "Course: " + getName() + " " + getNumber() + "\nAll course sections:\n";

		for (CourseOffering c : offeringList) {
			s += c + "\n";
		}

		return s;
	}
}
