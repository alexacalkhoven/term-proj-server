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
public class CourseOffering implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int secNum;
	private int secCap;
	private int studentAmount;
	private Course course;

	public CourseOffering(int secNum, int secCap) {
		setStudentAmount(0);
		setSecNum(secNum);
		setSecCap(secCap);
	}

	public void addRegistration(Registration registration) {
		// TODO: make it work
		setStudentAmount(getStudentAmount() + 1);
	}

	public void removeRegistration(Registration registration) {
		// TODO: make it work
		setStudentAmount(getStudentAmount() - 1);
	}

	public int getSecNum() {
		return secNum;
	}

	public void setSecNum(int secNumber) {
		this.secNum = secNumber;
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

	public String toString() {
		return "Section: " + getSecNum() + ", Spots: " + getStudentAmount() + " / " + getSecCap();
	}

	public int getStudentAmount() {
		return studentAmount;
	}

	public void setStudentAmount(int studentAmount) {
		this.studentAmount = studentAmount;
	}
}
