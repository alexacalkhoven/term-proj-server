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
	
	@BeforeClass
	public static void init() {
		db = new DBManager();
		com = new CommunicationManager();
		courseCatalogue = new CourseCatalogue(db);
		studentList = new StudentList(db);
		studentController = new StudentController(com, studentList, courseCatalogue);
	
		db.execute("INSERT INTO students VALUES (-1, 'TEST')");
		db.execute("INSERT INTO students VALUES(-2, 'TEST2')");
		
		studentController.studentLogin(-1);
	}
	
	@AfterClass
	public static void cleanup() {
		db.execute("DELETE FROM students WHERE id=-1");
		db.execute("DELETE FROM students WHERE id=-2");
	}
	
	@Test
	public void testSearch() {
		Assertions.assertNotNull(studentController.search(-1));
	}
	
	@Test
	public void testGetRegsFor() {
		Assertions.assertNotNull(studentController.getRegsFor(-1));
	}
	
	@Test
	public void testGetStudents() {
		Assertions.assertNotNull(studentController.getStudents());
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testCreateStudent() throws InvalidRequestException {
		studentController.createStudent(new Object[] { "TEST", -1 });
	}
	
	@Test
	public void testViewRegs() throws InvalidRequestException {
		Assertions.assertNotNull(studentController.viewRegs());
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testRegisterStudent() throws InvalidRequestException {
		studentController.registerStudent(-1);
	}
	
	@Test
	public void testRemoveStudent() throws InvalidRequestException {
		studentController.removeStudent(-2);
	}
	
	@Test
	public void testGetStudent() {
		Assertions.assertNotNull(studentController.getStudent());
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testDropCourse() throws InvalidRequestException {
		studentController.dropCourse(-1);
	}
}
