package main.controller;

import main.model.Course;
import main.model.StudentList;

public class StudentController {
	private CommunicationManager comManager;
	private StudentList studentList;
	
	public StudentController(CommunicationManager comManager) {
		this.comManager = comManager;
		this.studentList = new StudentList();
		this.comManager.registerHandlerClass(this);
	}
	
	@HandleRequest("student.remove")
	public Response removeStudent(Object[] args) {
		int number = (Integer)args[0];
		studentList.removeStudent(number);
		return new Response("test");
	}
}
