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
	private CourseCatalogue courseCatalogue;

	public CourseController(CommunicationManager comManager, CourseCatalogue courseCatalogue) {
		this.comManager = comManager;
		this.courseCatalogue = courseCatalogue;
		comManager.registerHandlerClass(this);
	}

	@HandleRequest("course.addOffering")
	public boolean createOffering(Object[] args) {
		Course toAdd = courseCatalogue.getCourse((String) args[2], (Integer) args[3]);
		if (toAdd == null) {
			return false;
		}
		int initLen = toAdd.getOfferingListLength();
		CourseOffering newOff = new CourseOffering((Integer) args[0], (Integer) args[1]);
		toAdd.addOffering(newOff);
		int finalLen = toAdd.getOfferingListLength();
		if (initLen + 1 == finalLen) {
			return true;
		}
		return false;
	}

	@HandleRequest("course.create")
	public boolean createCourse(Object[] args) {
		// returns true if successful
		return courseCatalogue.createCourse((String) args[0], (Integer) args[1]);
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

		int initLen = courseCatalogue.getCourseListLength();
		courseCatalogue.removeCourse(name, number);
		int finalLen = courseCatalogue.getCourseListLength();
		
		if (initLen - 1 == finalLen) {
			return true;
		}
		return false;
	}

}
