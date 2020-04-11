package main.controller;

import main.model.CourseCatalogue;
import main.model.StudentList;

public class StudentController {
	private CommunicationManager comManager;
	private StudentList studentList;
	private int id;
	private CourseCatalogue courseCatalogue;
	
	public StudentController(CommunicationManager comManager) {
		this.comManager = comManager;
		this.studentList = new StudentList();
		this.comManager.registerHandlerClass(this);
	}
	@HandleRequest("student.login")
	public void studentLogin(int id) {
		this.id = id;
	}
	@HandleRequest("student.deleteCourse")
	public void removeCourse(Object[] args) {
		String name = (String)args[0];
		int number = (Integer)args[1];
		studentList.searchStudent(id).removeRegistration(courseCatalogue.getCourse(name, number));
	}
	
}
