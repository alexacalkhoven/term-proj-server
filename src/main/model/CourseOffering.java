package main.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class with functions dealing with accessing the offeringList
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class CourseOffering implements Serializable {
	private static final long serialVersionUID = 1L;

	private int offeringId;
	private int secNum;
	private int secCap;
	private int studentAmount;
	private Course course;
	private ArrayList<Registration> registrationList;

	/**
	 * Creates a new CourseOffering with given section number and section capacity
	 * 
	 * @param secNum Offering section number
	 * @param secCap Offering student capacity
	 */
	public CourseOffering(int secNum, int secCap) {
		registrationList = new ArrayList<Registration>();
		setStudentAmount(0);
		setSecNum(secNum);
		setSecCap(secCap);
	}

	/**
	 * Adds a registration to the registration list
	 * 
	 * @param registration the registration to be added
	 */
	public void addRegistration(Registration registration) {
		registrationList.add(registration);
		setStudentAmount(getStudentAmount() + 1);
	}

	/**
	 * Removes a registration from the registration list
	 * 
	 * @param registration the registration to be removed
	 */
	public void removeRegistration(Registration registration) {
		registrationList.remove(registration);
		setStudentAmount(getStudentAmount() - 1);
	}

	public int getSecNum() {
		return secNum;
	}

	public void setSecNum(int secNumber) {
		this.secNum = secNumber;
	}

	public int getOfferingId() {
		return offeringId;
	}

	public void setOfferingId(int offeringId) {
		this.offeringId = offeringId;
	}

	public int getSecCap() {
		return secCap;
	}

	public boolean isFull() {
		return studentAmount >= secCap;
	}

	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Course getCourse() {
		return this.course;
	}

	public int getStudentAmount() {
		return studentAmount;
	}

	public void setStudentAmount(int studentAmount) {
		this.studentAmount = studentAmount;
	}
}
