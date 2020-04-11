package main.controller;

import main.model.Course;
import main.model.CourseCatalogue;

public class CourseController {
	private CommunicationManager comManager;
	private CourseCatalogue courseCatalogue;
	
	public CourseController(CommunicationManager comManager) {
		this.comManager = comManager;
		this.courseCatalogue = new CourseCatalogue();
		comManager.registerHandlerClass(this);
	}
	
	@HandleRequest("course.search")
	public Course searchCourse(Object[] args) {
		String name = (String)args[0];
		int number = (Integer)args[1];
		
		return courseCatalogue.searchCourse(name, number);
	}
	
	@HandleRequest("course.get")
	public Course getCourse(Object[] args) {
		String name = (String)args[0];
		int number = (Integer)args[1];
		
		return courseCatalogue.getCourse(name, number);
	}
}
