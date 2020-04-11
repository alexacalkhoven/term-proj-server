package main.controller;

import java.util.ArrayList;

import main.model.Course;
import main.model.CourseCatalogue;
import main.model.CourseOffering;
import main.model.Registration;
import main.model.Student;
import main.model.StudentList;

public class StudentController {
	private CommunicationManager comManager;
	private StudentList studentList;
	private Student student;
	private CourseCatalogue courseCatalogue;
	
	public StudentController(CommunicationManager comManager) {
		this.comManager = comManager;
		this.studentList = new StudentList();
		this.comManager.registerHandlerClass(this);
		student = null;
	}
	
	@HandleRequest("student.viewReg")
	public ArrayList<Registration> viewRegs(Object[] args){
		return student.getRegistrationList();
	}
	
	@HandleRequest("student.registerCourse")
	public boolean registerStudent(Object[] args) {
		String name = (String)args[0];
		int number = (Integer)args[1];
		int offeringNum = (Integer)args[2];
		Registration newReg = new Registration();
		//link this student to the Registration
		newReg.setStudent(student);
		//find desired course offering
		CourseOffering desiredCourseOff = courseCatalogue.getCourse(name, number).getCourseOffering(offeringNum);
		if(desiredCourseOff == null) {
			return false;
		}
		//if the course offering exists, link it to the Registration
		newReg.setOffering(desiredCourseOff);
		student.addRegistration(newReg);
		return true;
	}
	
	@HandleRequest("student.remove")
	public boolean removeStudent(Integer number) {
		int initLen = studentList.getLength();
		studentList.removeStudent(number);
		int finalLen = studentList.getLength();
		//returns true if successful (studentList is 1 Student shorter)
		if(initLen - 1 == finalLen) {
			return true;
		}
		return false;
	}

	@HandleRequest("student.login")
	public void studentLogin(Integer id) {
		this.student = studentList.searchStudent(id);
	}

	//is this the drop course function?
	@HandleRequest("student.deleteCourse")
	public void removeCourse(Object[] args) {
		String name = (String)args[0];
		int number = (Integer)args[1];
		student.removeRegistration(courseCatalogue.getCourse(name, number));
	}
}
