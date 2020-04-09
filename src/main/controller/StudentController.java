package main.controller;

import main.model.StudentList;

public class StudentController {
	private CommunicationManager comManager;
	private StudentList studentList;
	
	public StudentController(CommunicationManager comManager) {
		this.comManager = comManager;
		this.studentList = new StudentList();
	}
}
