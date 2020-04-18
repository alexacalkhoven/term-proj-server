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
	
	/**
	 * Gets registration list for a student
	 * 
	 * @return an ArrayList of all the Registrations
	 */
	@HandleRequest("student.search")
	public Student search(Integer studentId) {
		return studentList.getStudent(studentId);
	}	
	
	/**
	 * Gets registration list for a student
	 * 
	 * @return an ArrayList of all the Registrations
	 */
	@HandleRequest("student.getRegFor")
	public ArrayList<Registration> getRegsFor(Integer studentId) {
		return studentList.getStudent(studentId).getRegistrationList();
	}
	
	/**
	 * gets all of the students
	 * @return an ArrayList of all the students
	 */
	@HandleRequest("student.getAll")
	public ArrayList<Student> getStudents() {
		return studentList.getStudents();
	}
	
	/**
	 * creates a student
	 * @param args an object array holding the name and id of the student
	 * @throws InvalidRequestException thrown when function fails to create the student
	 */
	@HandleRequest("student.create")
	public void createStudent(Object[] args) throws InvalidRequestException {
		String name = (String)args[0];
		int id = (Integer)args[1];

		if (!studentList.addStudent(name, id)) {
			throw new InvalidRequestException("Failed to create student");
		}
	}
	/**
	 * gets all of the registrations for a student
	 * @return an ArrayList of the registrations for the student
	 * @throws InvalidRequestException Thrown when the function fails to retrieve the registrations for the student.
	 */
	@HandleRequest("student.regList")
	public ArrayList<Registration> viewRegs() throws InvalidRequestException {
		if (student == null) {
			throw new InvalidRequestException("Must be logged in");
		}
		
		return studentList.getRegistrations(student.getId());
	}
	/**
	 * Sets the grade of the student
	 * @param args the grade of the student.
	 */
	@HandleRequest("student.setGrade")
	public void setGrade(Object[] args) {
		// TODO: make it work
	}
	/**
	 * Registers the student for a courseOffering.
	 * @param offeringId The offeringId for the desired courseOffering
	 * @throws InvalidRequestException Thrown when the student is not specified.
	 */
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
	/**
	 * Removes a student from the studentList
	 * @param id the id of the target student
	 * @throws InvalidRequestException Thrown when function fails to remove the student.
	 */
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
	/**
	 * Gets a student based on a student id
	 * @param id the id of the student
	 * @return the student with the matching id
	 */
	@HandleRequest("student.login")
	public Student studentLogin(Integer id) {
		student = studentList.getStudent(id);
		return student;
	}
	/**
	 * Drops a course that a student is registered for.
	 * @param offeringId the offering id of the course that the student wishes to drop
	 * @throws InvalidRequestException Thrown when the function fails to drop the course for the student.
	 */
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
