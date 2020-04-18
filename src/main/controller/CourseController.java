package main.controller;

import java.util.ArrayList;

import main.model.Course;
import main.model.CourseCatalogue;
import main.model.CourseOffering;

/**
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

	@HandleRequest("course.addOffering")
	public void createOffering(Object[] args) throws InvalidRequestException {
		int courseId = (Integer)args[0];
		int secNum = (Integer)args[1];
		int secCap = (Integer)args[2];
		
		if (!courseCatalogue.createCourseOffering(courseId, secNum, secCap)) {
			throw new InvalidRequestException("Failed to create course offering");
		}
	}
	
	@HandleRequest("course.removeOffering")
	public void removeOffering(Integer offeringId) throws InvalidRequestException {
		if (!courseCatalogue.removeCourseOffering(offeringId)) {
			throw new InvalidRequestException("Failed to remove course offering");
		}
	}

	@HandleRequest("course.create")
	public void createCourse(Object[] args) throws InvalidRequestException {
		String name = (String)args[0];
		int number = (Integer)args[1];
		
		if (!courseCatalogue.createCourse(name, number)) {
			throw new InvalidRequestException("Failed to create new course");
		}
	}

	@HandleRequest("course.search")
	public Course searchCourse(Object[] args) {
		String name = (String) args[0];
		int number = (Integer) args[1];

		return courseCatalogue.getCourse(name, number);
	}

	@HandleRequest("course.get")
	public ArrayList<Course> getCourses() {
		return courseCatalogue.getCourses();
	}

	@HandleRequest("course.remove")
	public void removeCourse(Integer courseId) throws InvalidRequestException {
		if (!courseCatalogue.removeCourse(courseId)) {
			throw new InvalidRequestException("Failed to remove course");
		}
	}

	@HandleRequest("course.getPreReqs")
	public ArrayList<Course> getPrereqs(Integer courseId) {
		return courseCatalogue.getPreReqs(courseId);
	}
	
	@HandleRequest("course.getOfferings")
	public ArrayList<CourseOffering> getOfferings(Integer courseId) {
		return courseCatalogue.getOfferings(courseId);
	}
	
	@HandleRequest("course.addPreReq")
	public void addPreReq(Object[] args) throws InvalidRequestException {
		int parentCourseId = (Integer)args[0];
		int childCourseId = (Integer)args[1];
		
		if (!courseCatalogue.addPreReq(parentCourseId, childCourseId)) {
			throw new InvalidRequestException("Failed to add prereq");
		}
	}
	
	@HandleRequest("course.removePreReq")
	public void removePreReq(Object[] args) throws InvalidRequestException {
		int parentCourseId = (Integer)args[0];
		int childCourseId = (Integer)args[1];
		
		if (!courseCatalogue.removePreReq(parentCourseId, childCourseId)) {
			throw new InvalidRequestException("Failed to remove pre req");
		}
	}
}
