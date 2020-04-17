package main.controller;

import java.util.ArrayList;

import main.model.Course;
import main.model.CourseCatalogue;
import main.model.CourseOffering;
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
	public boolean createStudent(Object[] args){
		String name = (String)args[0];
		int id = (Integer)args[1];

		return studentList.addStudent(name, id);
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
	
	// TODO: deal with prereqs
	@HandleRequest("student.addRegCourse")
	public boolean registerStudent(Integer offeringId) throws InvalidRequestException {
		if (student == null) {
			throw new InvalidRequestException("Must be logged in");
		}
		
		CourseOffering offering = courseCatalogue.getOffering(offeringId);
		ArrayList<Registration> regs = studentList.getRegistrations(student.getId());
		ArrayList<Course> preReqs = courseCatalogue.getPreReqs(offering.getCourse().getCourseId());
		
		for (Course preReq : preReqs) {
			boolean hasReg = true;
			
			for (Registration reg : regs) {
				
			}
		}
		
		studentList.createRegistration(student.getId(), offeringId);
		return true;
	}
	
	@HandleRequest("student.remove")
	public boolean removeStudent(Integer id) {
		return studentList.removeStudent(id);
	}

	@HandleRequest("student.login")
	public Student studentLogin(Integer id) {
		student = studentList.getStudent(id);
		return student;
	}

	@HandleRequest("student.dropCourse")
	public boolean dropCourse(Integer courseId) throws InvalidRequestException {
		if (student == null) {
			throw new InvalidRequestException("Must be logged in");
		}
		
		studentList.removeRegistration(student.getId(), courseId);
		return true;
	}
}
