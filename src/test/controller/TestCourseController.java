package test.controller;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import main.controller.CommunicationManager;
import main.controller.CourseController;
import main.controller.DBManager;
import main.controller.InvalidRequestException;
import main.model.CourseCatalogue;

/**
 * Runs the tests for the CourseController
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
public class TestCourseController {
	private static CommunicationManager com;
	private static DBManager db;
	private static CourseCatalogue courseCatalogue;
	private static CourseController courseController;

	/**
	 * Initializes the environment for all tests to run in
	 */
	@BeforeClass
	public static void init() {
		db = new DBManager();
		com = new CommunicationManager();
		courseCatalogue = new CourseCatalogue(db);
		courseController = new CourseController(com, db, courseCatalogue);

		db.execute("INSERT INTO courses VALUES (-1, 'TEST', 100)");
		db.execute("INSERT INTO courses VALUES (-2, 'TEST', 200)");
	}
	
	/**
	 * Cleans up after the tests are done
	 */
	@AfterClass
	public static void cleanup() {
		db.execute("DELETE FROM courses WHERE id=-1");
		db.execute("DELETE FROM courses WHERE id=-2");
		db.execute("DELETE FROM offerings WHERE course_id=-1");
		db.execute("DELETE FROM prerequisites WHERE parent_id=-1");
	}

	/**
	 * Tests that the getCourses method does not return null
	 */
	@Test
	public void testGetCourses() {
		Assertions.assertNotNull(courseController.getCourses());
	}

	/**
	 * Tests that the searchById method does not return null
	 */
	@Test
	public void testSearchById() {
		Assertions.assertNotNull(courseController.searchCourse(-1));
	}

	/**
	 * Tests that the createCourse method does not allow you to create two identical courses
	 * 
	 * @throws InvalidRequestException if the tested function fails
	 */
	@Test(expected = InvalidRequestException.class)
	public void testCreateCourse() throws InvalidRequestException {
		courseController.createCourse(new Object[] { "TEST", 100 });
	}
	
	/**
	 * Tests that the removeCourse method does not let you remove non-existent courses
	 * 
	 * @throws InvalidRequestException if the tested function fails
	 */
	@Test(expected = InvalidRequestException.class)
	public void testRemoveCourse() throws InvalidRequestException {
		courseController.removeCourse(-100);
	}
	
	/**
	 * Tests that the getPreReqs method does not return null
	 */
	@Test
	public void testGetPreReqs() {
		Assertions.assertNotNull(courseController.getPrereqs(-1));
	}
	
	/**
	 * Tests that the getOffering method does not let you return a non-existent offering
	 * 
	 * @throws InvalidRequestException if the tested function fails
	 */
	@Test(expected = InvalidRequestException.class)
	public void testGetOffering() throws InvalidRequestException {
		courseController.getOffering(new Object[] { -1, 100 });
	}
	
	/**
	 * Tests that the removeCourse method does not return null
	 */
	@Test
	public void testAddPreReq() throws InvalidRequestException {
		courseController.addPreReq(new Object[] { -1, -2 });
	}
	
	/**
	 * Tests that the removePreReq method does not let you remove non-existent prereqs
	 * 
	 * @throws InvalidRequestException if the tested function fails
	 */
	@Test(expected = InvalidRequestException.class)
	public void testRemovePreReq() throws InvalidRequestException {
		courseController.removePreReq(new Object[] { -1, -100 });
	}

	/**
	 * Tests that the search method does not return null
	 */
	@Test
	public void testSearch() {
		Assertions.assertNotNull(courseController.searchCourse(new Object[] { "TEST", 100 }));
	}
	
	/**
	 * Tests that the getOfferings method does not return null
	 */
	@Test
	public void testGetOfferings() {
		Assertions.assertNotNull(courseController.getOfferings(-1));
	}
	
	/**
	 * Tests that the addOffering method can create an offering
	 */
	@Test
	public void testAddOffering() throws InvalidRequestException {
		courseController.createOffering(new Object[] { -1, 1, 100 });
	}
	
	/**
	 * Tests that the removeOffering method does not let you remove a non-existent offering
	 * 
	 * @throws InvalidRequestException if the tested function fails
	 */
	@Test(expected = InvalidRequestException.class)
	public void testRemoveOffering() throws InvalidRequestException {
		courseController.removeOffering(-100);
	}
}
