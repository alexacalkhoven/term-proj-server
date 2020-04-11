package main.model;

import java.io.Serializable;
import java.util.ArrayList;

import main.controller.CommunicationManager;

public class StudentList implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Student> studentList;

	public StudentList() {
		studentList = new ArrayList<Student>();
	}
	
	public int getLength() {
		return studentList.size();
	}

	public void addStudent(String name, int id) {
		Student student = getStudent(id);

		if (student != null) {
			System.err.println("Student with this ID already exists: ");
			System.err.println(student);

			return;
		}

		student = new Student(name, id);
		studentList.add(student);
		System.out.println("Created new student:\n" + student);
	}

	public void removeStudent(int id) {
		Student student = searchStudent(id);

		if (student == null)
			return;

		studentList.remove(student);
		System.out.println("Removed student:\n" + student);
	}

	private Student getStudent(int id) {
		for (Student s : studentList) {
			if (s.getId() == id)
				return s;
		}

		return null;
	}

	public Student searchStudent(int id) {
		Student student = getStudent(id);

		if (student == null) {
			System.err.println("Error, could not find student with ID: " + id);
		}

		return student;
	}
}
