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

	@BeforeClass
	public static void init() {
		db = new DBManager();
		com = new CommunicationManager();
		courseCatalogue = new CourseCatalogue(db);
		courseController = new CourseController(com, db, courseCatalogue);

		db.execute("INSERT INTO courses VALUES (-1, 'TEST', 100)");
		db.execute("INSERT INTO courses VALUES (-2, 'TEST', 200)");
	}

	@AfterClass
	public static void cleanup() {
		db.execute("DELETE FROM courses WHERE id=-1");
		db.execute("DELETE FROM courses WHERE id=-2");
		db.execute("DELETE FROM offerings WHERE course_id=-1");
		db.execute("DELETE FROM prerequisites WHERE parent_id=-1");
	}

	@Test
	public void testGetCourses() {
		Assertions.assertNotNull(courseController.getCourses());
	}

	@Test
	public void testSearchById() {
		Assertions.assertNotNull(courseController.searchCourse(-1));
	}

	@Test(expected = InvalidRequestException.class)
	public void testCreateCourse() throws InvalidRequestException {
		courseController.createCourse(new Object[] { "TEST", 100 });
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testRemoveCourse() throws InvalidRequestException {
		courseController.removeCourse(-100);
	}
	
	@Test
	public void testGetPreReqs() {
		Assertions.assertNotNull(courseController.getPrereqs(-1));
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testGetOffering() throws InvalidRequestException {
		courseController.getOffering(new Object[] { -1, 100 });
	}
	
	@Test
	public void testAddPreReq() throws InvalidRequestException {
		courseController.addPreReq(new Object[] { -1, -2 });
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testRemovePreReq() throws InvalidRequestException {
		courseController.removePreReq(new Object[] { -1, -100 });
	}

	@Test
	public void testSearch() {
		Assertions.assertNotNull(courseController.searchCourse(new Object[] { "TEST", 100 }));
	}
	
	@Test
	public void testGetOfferings() {
		Assertions.assertNotNull(courseController.getOfferings(-1));
	}
	
	@Test
	public void testAddOffering() throws InvalidRequestException {
		courseController.createOffering(new Object[] { -1, 1, 100 });
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testRemoveOffering() throws InvalidRequestException {
		courseController.removeOffering(-100);
	}
}
