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

	public boolean addRegistration(Registration registration) {
		if (registrationList.contains(registration)) return false;
		
		registrationList.add(registration);
		return true;
	}

	public boolean isRegistered(Course course) {
		for (Registration reg : registrationList) {
			if (reg.getOffering().getCourse().equals(course)) {
				return true;
			}
		}

		return false;
	}

	public boolean removeRegistration(Course course, DBManager db) {
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

	public ArrayList<Registration> getRegistrationList(DBManager db) {
		ArrayList<Registration> registrationList = new ArrayList<Registration>();
		Registration registration = new Registration();
		CourseOffering offering = null;
		
		ResultSet res = db.query("SELECT * FROM registrations");
		try {
			while(res.next()){
				int offeringid = res.getInt(3);
				String grade = res.getString(4);
				
				ResultSet res2 = db.query("SELECT * FROM offerings WHERE course_number = ?", offeringid);
				int secCap = res2.getInt(2);
				int secNum = res2.getInt(1);
				offering = new CourseOffering(secCap, secNum);
				char[] a = grade.toCharArray();
				registration.setStudent(this);
				registration.setOffering(offering);
				registration.setGrade(a[0]);
				registrationList.add(registration);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return registrationList;

	}
}
