package main.controller;

import java.util.ArrayList;

import main.model.CourseCatalogue;
import main.model.Registration;
import main.model.Student;
import main.model.StudentList;

/**
 * Handles all of the network functionality related to a student
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class StudentController {
	private CommunicationManager comManager;
	private DBManager db;
	private StudentList studentList;
	private Student student;
	private CourseCatalogue courseCatalogue;
	
	public StudentController(CommunicationManager comManager, DBManager db, StudentList studentList, CourseCatalogue courseCatalogue) {
		this.comManager = comManager;
		this.db = db;
		this.studentList = studentList;
		this.courseCatalogue = courseCatalogue;
		this.comManager.registerHandlerClass(this);
		student = null;
	}
	
	@HandleRequest("student.create")
	public void createStudent(Object[] args) throws InvalidRequestException {
		String name = (String)args[0];
		int id = (Integer)args[1];

		if (!studentList.addStudent(name, id)) {
			throw new InvalidRequestException("Failed to create student");
		}
	}
	
	@HandleRequest("student.regList")
	public ArrayList<Registration> viewRegs() throws InvalidRequestException {
		if (student == null) {
			throw new InvalidRequestException("Must be logged in");
		}
		
		return studentList.getRegistrations(student.getId());
	}
	
	@HandleRequest("student.setGrade")
	public void setGrade(Object[] args) {
		// TODO: make it work
	}
	
	@HandleRequest("student.addRegCourse")
	public void registerStudent(Integer offeringId) throws InvalidRequestException {
		if (student == null) {
			throw new InvalidRequestException("Must be logged in");
		}
		
		if (!studentList.createRegistration(student.getId(), offeringId)) {
			throw new InvalidRequestException("Failed to create registration");
		}
	}
	
	@HandleRequest("student.remove")
	public void removeStudent(Integer id) throws InvalidRequestException {
		if (!studentList.removeStudent(id)) {
			throw new InvalidRequestException("Failed to remove student");
		}
	}

	@HandleRequest("student.login")
	public Student studentLogin(Integer id) {
		student = studentList.getStudent(id);
		return student;
	}

	@HandleRequest("student.dropCourse")
	public void dropCourse(Integer courseId) throws InvalidRequestException {
		if (student == null) {
			throw new InvalidRequestException("Must be logged in");
		}
		
		if (!studentList.removeRegistration(student.getId(), courseId)) {
			throw new InvalidRequestException("Failed to remove student course registration");
		}
	}
}
