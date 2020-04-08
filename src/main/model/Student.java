package main.model;

import java.util.ArrayList;

public class Student {
	private String studentName;
	private int studentId;
	private ArrayList<Registration> registrationList;

	public Student(String name, int id) {
		registrationList = new ArrayList<Registration>();
		setStudentName(name);
		setStudentId(id);
	}

	public void addRegistration(Registration registration) {
		registrationList.add(registration);
	}

	public boolean isRegistered(Course course) {
		for (Registration reg : registrationList) {
			if (reg.getOffering().getCourse().equals(course))
				return true;
		}

		return false;
	}

	public void removeRegistration(Course course) {
		for (Registration reg : registrationList) {
			if (reg.getOffering().getCourse().equals(course)) {
				System.out.println("Dropped course: " + course.getFullName());
				reg.removeRegistration();
				registrationList.remove(reg);
				return;
			}
		}

		System.err.println("Error, student not registered for " + course.getFullName());
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String toString() {
		String s = "Student name: " + getStudentName() + ", Student ID: " + getStudentId() + "\n";

		s += "Registered courses:\n";

		for (Registration reg : registrationList) {
			s += "\n\n" + reg;
		}

		return s;
	}
}
