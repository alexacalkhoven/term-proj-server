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

	private CommunicationManager com;
	private DBManager db;
	private CourseCatalogue courseCatalogue;
	private CourseController courseController;

	@BeforeClass
	public void init() {
		db = new DBManager();
		com = new CommunicationManager();
		courseCatalogue = new CourseCatalogue(db);
		courseController = new CourseController(com, db, courseCatalogue);

		db.execute("INSERT INTO courses VALUES (-1, 'TEST', 100)");
	}

	@AfterClass
	public void cleanup() {
		db.execute("DELETE FROM courses WHERE id=-1");
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

	@Test
	public void testSearch() {
		Assertions.assertNotNull(courseController.searchCourse(new Object[] { "TEST", 100 }));
	}
}
