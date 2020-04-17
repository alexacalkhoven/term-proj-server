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
	public boolean createOffering(Object[] args) {
		int courseId = (Integer)args[0];
		int secNum = (Integer)args[1];
		int secCap = (Integer)args[2];
		
		return courseCatalogue.createCourseOffering(courseId, secNum, secCap);
	}

	@HandleRequest("course.create")
	public boolean createCourse(Object[] args) {
		String name = (String)args[0];
		int number = (int)args[1];
		
		// returns true if successful
		return courseCatalogue.createCourse(name, number);
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
	public boolean removeCourse(Object[] args) {
		String name = (String) args[0];
		int number = (Integer) args[1];

		return courseCatalogue.removeCourse(name, number);
	}

	@HandleRequest("course.getPreReqs")
	public ArrayList<Course> getPrereqs(Object[] args) {
		int courseId = (Integer)args[0];
		
		return courseCatalogue.getPreReqs(courseId);
	}
	
	@HandleRequest("course.getOfferings")
	public ArrayList<CourseOffering> getOfferings(Object[] args) {
		int courseId = (Integer)args[0];
		
		return courseCatalogue.getOfferings(courseId);
	}
}
