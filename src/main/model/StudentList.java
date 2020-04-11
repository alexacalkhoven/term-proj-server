package main.model;

import java.io.Serializable;
import java.util.ArrayList;

import main.controller.CommunicationManager;

public class StudentList implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Student> studentList;

	/**
	 * Initialize the student list, add test students
	 */
	public StudentList() {
		studentList = new ArrayList<Student>();
		
		studentList.add(new Student("Test", 1));
		studentList.add(new Student("Alexa", 3));
	}
	
	/**
	 * Gets the amount of students
	 * @return The length of the student list
	 */
	public int getLength() {
		return studentList.size();
	}

	/**
	 * Creates a new student with given name and id, and adds it to the list
	 * @param name Student name
	 * @param id Student id
	 * @return Whether it was successful or not
	 */
	public boolean addStudent(String name, int id) {
		Student student = getStudent(id);

		if (student != null) {
			System.err.println("Student with this ID already exists: ");
			System.err.println(student);

			return false;
		}

		student = new Student(name, id);
		studentList.add(student);
		System.out.println("Created new student:\n" + student);
		return true;
	}

	/**
	 * Removes student from list with given ID
	 * @param id Student ID to remove
	 * @return Whether the remove was successful or not
	 */
	public boolean removeStudent(int id) {
		Student student = getStudent(id);

		if (student == null)
			return false;

		System.out.println("Removed student:\n" + student);
		return studentList.remove(student);
	}

	/**
	 * Returns student from list with given ID
	 * @param id Student ID to search for
	 * @return Student with given ID, or null if none found
	 */
	public Student getStudent(int id) {
		for (Student s : studentList) {
			if (s.getId() == id)
				return s;
		}

		return null;
	}
}
