package main.controller;

import java.util.ArrayList;

import main.model.Course;
import main.model.CourseCatalogue;
import main.model.CourseOffering;

/**
 * The course controller that handles requests to the courseList/offeringList
 * 
 * @author Alexa Calkhoven
 * @author Radu Schirliu
 * @author Jordan Kwan
 *
 */
@SuppressWarnings("unused")
public class CourseController {
	private CommunicationManager comManager;
	private DBManager db;
	private CourseCatalogue courseCatalogue;

	public CourseController(CommunicationManager comManager, DBManager db, CourseCatalogue courseCatalogue) {
		this.comManager = comManager;
		this.db = db;
		this.courseCatalogue = courseCatalogue;
		comManager.registerHandlerClass(this);
	}

	/**
	 * Searches for a course by ID
	 * @param id ID to search four
	 * @return The course with given ID, or null if none found
	 */
	@HandleRequest("course.searchById")
	public Course searchCourse(Integer id) {
		return courseCatalogue.getCourse(id);
	}

	/**
	 * Searches for offering with given parameters
	 * @param args Holds the courseId and sectionNumber that the client must pass
	 * @return The ID of the given Offering
	 * @throws InvalidRequestException If there is no offering found
	 */
	@HandleRequest("course.getOfferingId")
	public Integer getOffering(Object[] args) throws InvalidRequestException {
		int courseId = (Integer) args[0];
		int secNum = (Integer) args[1];
		int result = courseCatalogue.getCourseOfferingId(courseId, secNum);
		if (result == -1) {
			throw new InvalidRequestException("OfferingId not found.");
		}
		return result;
	}

	/**
	 * calls createCourseOffering and adds a course offering to the courseCatalogue
	 * with an courseID, section number, and section cap.
	 * 
	 * @param args An object array that holds the course ID, section number, and
	 *             section cap.
	 * @throws InvalidRequestException This exception is thrown when
	 *                                 createCourseOffering fails to create the
	 *                                 course offering.
	 */
	@HandleRequest("course.addOffering")
	public void createOffering(Object[] args) throws InvalidRequestException {
		int courseId = (Integer) args[0];
		int secNum = (Integer) args[1];
		int secCap = (Integer) args[2];

		if (!courseCatalogue.createCourseOffering(courseId, secNum, secCap)) {
			throw new InvalidRequestException("Failed to create course offering");
		}
	}

	/**
	 * calls removeCourseOffering from courseCatalogue and removes the course
	 * offering with the specified offeringId.
	 * 
	 * @param offeringId The Id of the offering to be removed.
	 * @throws InvalidRequestException This exception is thrown when removeOffering
	 *                                 fails to remove the course offering.
	 */
	@HandleRequest("course.removeOffering")
	public void removeOffering(Integer offeringId) throws InvalidRequestException {
		if (!courseCatalogue.removeCourseOffering(offeringId)) {
			throw new InvalidRequestException("Failed to remove course offering");
		}
	}

	/**
	 * calls createCourse in courseCatalogue and creates a new course in the
	 * courseCatalogue with a given name and number.
	 * 
	 * @param args An object array that contains the course name and course number.
	 * @throws InvalidRequestException This exception is thrown when createCourse
	 *                                 fails to create the new course.
	 */
	@HandleRequest("course.create")
	public void createCourse(Object[] args) throws InvalidRequestException {
		String name = (String) args[0];
		int number = (Integer) args[1];

		if (!courseCatalogue.createCourse(name, number)) {
			throw new InvalidRequestException("Failed to create new course");
		}
	}

	/**
	 * calls getCourse from the courseCatalogue and finds the desired course based
	 * on a given course name and course number.
	 * 
	 * @param args an object array containing the course name and course number.
	 * @return Returns the desired course.
	 */
	@HandleRequest("course.search")
	public Course searchCourse(Object[] args) {
		String name = (String) args[0];
		int number = (Integer) args[1];

		return courseCatalogue.getCourse(name, number);
	}

	/**
	 * calls getCourses from courseCatalogue and gets all of the courses in the
	 * courseCatalogue.
	 * 
	 * @return Returns an ArrayList of all the courses.
	 */
	@HandleRequest("course.get")
	public ArrayList<Course> getCourses() {
		return courseCatalogue.getCourses();
	}

	/**
	 * calls removeCourse from courseCatalogue and removes a course with the
	 * specified courseId.
	 * 
	 * @param courseId The id of the target course.
	 * @throws InvalidRequestException This exception is thrown when removeCourse
	 *                                 fails to remove the course.
	 */
	@HandleRequest("course.remove")
	public void removeCourse(Integer courseId) throws InvalidRequestException {
		if (!courseCatalogue.removeCourse(courseId)) {
			throw new InvalidRequestException("Failed to remove course");
		}
	}

	/**
	 * calls getPreReqs from courseCatalogue and returns a list of the prerequisites
	 * of a course based on courseId.
	 * 
	 * @param courseId The id of the target course
	 * @return Returns an ArrayList of prerequisites to the target course.
	 */

	@HandleRequest("course.getPreReqs")
	public ArrayList<Course> getPrereqs(Integer courseId) {
		return courseCatalogue.getPreReqs(courseId);
	}

	/**
	 * calls getOfferings from courseCatalogue and returns a list of the offerings
	 * based on courseId.
	 * 
	 * @param courseId The courseId of the target course
	 * @return Returns an ArrayList of CourseOffering.
	 */
	@HandleRequest("course.getOfferings")
	public ArrayList<CourseOffering> getOfferings(Integer courseId) {
		return courseCatalogue.getOfferings(courseId);
	}

	/**
	 * Adds a prerequisite to the courseCatalogue
	 * 
	 * @param args object array containing parentCourseId and childCourseId.
	 * @throws InvalidRequestException Thrown when this function fails to add the
	 *                                 specified prerequisite.
	 */
	@HandleRequest("course.addPreReq")
	public void addPreReq(Object[] args) throws InvalidRequestException {
		int parentCourseId = (Integer) args[0];
		int childCourseId = (Integer) args[1];

		if (parentCourseId == childCourseId) {
			throw new InvalidRequestException("Course cannot be prereq for itself");
		}

		if (!courseCatalogue.addPreReq(parentCourseId, childCourseId)) {
			throw new InvalidRequestException("Failed to add prereq");
		}
	}

	/**
	 * Removes a prerequisite from the courseCatalogue.
	 * 
	 * @param args object array containing parentCourseId and childCourseId.
	 * @throws InvalidRequestException Thrown when this function fails to remove the
	 *                                 specified prerequisite.
	 */
	@HandleRequest("course.removePreReq")
	public void removePreReq(Object[] args) throws InvalidRequestException {
		int parentCourseId = (Integer) args[0];
		int childCourseId = (Integer) args[1];

		if (!courseCatalogue.removePreReq(parentCourseId, childCourseId)) {
			throw new InvalidRequestException("Failed to remove pre req");
		}
	}
}
