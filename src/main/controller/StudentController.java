package main.controller;

import java.util.ArrayList;

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
	
	@HandleRequest("students.getAll")
	public ArrayList<Student> getStudents() {
		return studentList.getStudents();
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
		
		ArrayList<Registration> regs = studentList.getRegistrations(student.getId());
		CourseOffering offering = courseCatalogue.getOffering(offeringId);
		int courseId = offering.getCourse().getCourseId();
		
		for (Registration reg : regs) {
			if (reg.getOffering().getCourse().getCourseId() == courseId) {
				throw new InvalidRequestException("Already registered for a different offering for this course");
			}
		}
		
		if (!studentList.createRegistration(student.getId(), offeringId)) {
			throw new InvalidRequestException("Failed to create registration");
		}
		
		courseCatalogue.updateStudentCount(offeringId, 1);
	}
	
	@HandleRequest("student.remove")
	public void removeStudent(Integer studentId) throws InvalidRequestException {
		ArrayList<Registration> regs = studentList.getRegistrations(studentId);
		
		for (Registration reg : regs) {
			int offeringId = reg.getOffering().getOfferingId();
			courseCatalogue.updateStudentCount(offeringId, -1);
		}
		
		if (!studentList.removeStudent(studentId)) {
			throw new InvalidRequestException("Failed to remove student");
		}
	}

	@HandleRequest("student.login")
	public Student studentLogin(Integer id) {
		student = studentList.getStudent(id);
		return student;
	}

	@HandleRequest("student.dropCourse")
	public void dropCourse(Integer offeringId) throws InvalidRequestException {
		if (student == null) {
			throw new InvalidRequestException("Must be logged in");
		}
		
		if (!studentList.removeRegistration(student.getId(), offeringId)) {
			throw new InvalidRequestException("Failed to remove student course registration");
		}
		
		courseCatalogue.updateStudentCount(offeringId, -1);
	}
}
