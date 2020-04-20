package test.controller;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import main.controller.CommunicationManager;
import main.controller.DBManager;
import main.controller.InvalidRequestException;
import main.controller.StudentController;
import main.model.CourseCatalogue;
import main.model.StudentList;

/**
 * Run the tests for the StudentController
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class TestStudentController {
	private static CommunicationManager com;
	private static DBManager db;
	private static CourseCatalogue courseCatalogue;
	private static StudentList studentList;
	private static StudentController studentController;
	
	/**
	 * Initializes the test environment
	 */
	@BeforeClass
	public static void init() {
		db = new DBManager();
		com = new CommunicationManager();
		courseCatalogue = new CourseCatalogue(db);
		studentList = new StudentList(db);
		studentController = new StudentController(com, studentList, courseCatalogue);
	
		db.execute("INSERT INTO students VALUES (-1, 'TEST')");
		db.execute("INSERT INTO students VALUES (-2, 'TEST2')");
		
		studentController.studentLogin(-1);
	}
	
	/**
	 * Cleans up the test environment after the tests
	 */
	@AfterClass
	public static void cleanup() {
		db.execute("DELETE FROM students WHERE id=-1");
		db.execute("DELETE FROM students WHERE id=-2");
	}
	
	/**
	 * Tests that the search method does not return null
	 */
	@Test
	public void testSearch() {
		Assertions.assertNotNull(studentController.search(-1));
	}
	
	/**
	 * Tests that the getRegsFor method does not return null
	 */
	@Test
	public void testGetRegsFor() {
		Assertions.assertNotNull(studentController.getRegsFor(-1));
	}
	
	/**
	 * Tests that the getStudents method does not return null
	 */
	@Test
	public void testGetStudents() {
		Assertions.assertNotNull(studentController.getStudents());
	}
	
	/**
	 * Tests that the createStudent method does not let you create two students with identical IDs
	 * 
	 * @throws InvalidRequestException if the tested function fails
	 */
	@Test(expected = InvalidRequestException.class)
	public void testCreateStudent() throws InvalidRequestException {
		studentController.createStudent(new Object[] { "TEST", -1 });
	}
	
	/**
	 * Tests that the viewRegs method does not return null
	 * 
	 * @throws InvalidRequestException if the tested function fails
	 */
	@Test
	public void testViewRegs() throws InvalidRequestException {
		Assertions.assertNotNull(studentController.viewRegs());
	}
	
	/**
	 * Tests that the registerStudent method does not let you register for non-existent offerings
	 * 
	 * @throws InvalidRequestException if the tested function fails
	 */
	@Test(expected = InvalidRequestException.class)
	public void testRegisterStudent() throws InvalidRequestException {
		studentController.registerStudent(-1);
	}
	
	/**
	 * Tests that the removeStudent method lets you remove a student
	 * 
	 * @throws InvalidRequestException if the tested function fails
	 */
	@Test
	public void testRemoveStudent() throws InvalidRequestException {
		studentController.removeStudent(-2);
	}
	
	/**
	 * Tests that the getStudent method does not return null
	 */
	@Test
	public void testGetStudent() {
		Assertions.assertNotNull(studentController.getStudent());
	}
	
	/**
	 * Tests that the dropCourse method does let a student drop a course they are not enrolled in
	 * 
	 * @throws InvalidRequestException if the tested function fails
	 */
	@Test(expected = InvalidRequestException.class)
	public void testDropCourse() throws InvalidRequestException {
		studentController.dropCourse(-1);
	}
}
