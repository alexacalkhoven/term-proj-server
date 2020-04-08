package main.controller;
import java.util.ArrayList;

import main.model.Student;

public class StudentList
{
	private ArrayList<Student> studentList;
	
	public StudentList()
	{
		studentList = new ArrayList<Student>();
	}
	
	public void addStudent(String name, int id)
	{
		Student student = getStudent(id);
		
		if (student != null)
		{
			System.err.println("Student with this ID already exists: ");
			System.err.println(student);
			
			return;
		}
		
		student = new Student(name, id);
		studentList.add(student);
		System.out.println("Created new student:\n" + student);
	}
	
	public void removeStudent(int id)
	{
		Student student = searchStudent(id);
		
		if (student == null) return;
		
		studentList.remove(student);
		System.out.println("Removed student:\n" + student);
	}
	
	private Student getStudent(int id)
	{
		for (Student s : studentList)
		{
			if (s.getStudentId() == id) return s;
		}
		
		return null;
	}
	
	public Student searchStudent(int id)
	{
		Student student = getStudent(id);
		
		if (student == null)
		{
			System.err.println("Error, could not find student with ID: " + id);
		}
		
		return student;
	}
}
