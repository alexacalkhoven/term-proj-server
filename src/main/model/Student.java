package main.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that simulates a student holding an id, name and list of registrations that the student may have.
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int id;
	private ArrayList<Registration> registrationList;

	public Student(String name, int id) {
		registrationList = new ArrayList<Registration>();
		setName(name);
		setId(id);
	}
	/**
	 * Adds a registration for the student.
	 * @param registration the registration to be added
	 * @return returns true when the registration is added, returns false if the registration does not exist.
	 */
	public boolean addRegistration(Registration registration) {
		if (registrationList.contains(registration)) return false;
		
		registrationList.add(registration);
		return true;
	}
	/**
	 * checks to see if the student is registered for a course
	 * @param course the course that is checked
	 * @return returns true if the student is registered for the given course
	 */
	public boolean isRegistered(Course course) {
		for (Registration reg : registrationList) {
			if (reg.getOffering().getCourse().equals(course)) {
				return true;
			}
		}

		return false;
	}
	/**
	 * removes registration for the student.
	 * @param course the course that the student is registered for
	 * @return returns true if the student is successfully removed from the course, returns false if the student is not registered for the given course.
	 */
	public boolean removeRegistration(Course course) {
		for (Registration reg : registrationList) {
			if (reg.getOffering().getCourse().equals(course)) {
				System.out.println("Dropped course: " + course.getFullName());
				reg.removeRegistration();
				registrationList.remove(reg);
				return true;
			}
		}

		System.err.println("Error, student not registered for " + course.getFullName());
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String studentName) {
		this.name = studentName;
	}

	public int getId() {
		return id;
	}

	public void setId(int studentId) {
		this.id = studentId;
	}

	public String toString() {
		String s = "Student name: " + getName() + ", Student ID: " + getId() + "\n";

		s += "Registered courses:\n";

		for (Registration reg : registrationList) {
			s += "\n\n" + reg;
		}

		return s;
	}

	public ArrayList<Registration> getRegistrationList() {
		return registrationList;
	}
}
