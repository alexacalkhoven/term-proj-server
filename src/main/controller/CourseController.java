package main.controller;

import main.model.CourseCatalogue;

public class CourseController {
	private CommunicationManager comManager;
	private CourseCatalogue courseCatalogue;
	
	public CourseController(CommunicationManager comManager) {
		this.comManager = comManager;
		this.courseCatalogue = new CourseCatalogue();
		comManager.registerHandlerClass(this);
	}
	
	@HandleRequest("searchCourse")
	public String searchCourse(String id) {
		return "hi aloxo, you searched for: " + id;
	}
	
	@HandleRequest("courseExists")
	public boolean courseExists(String id) {
		return true;
	}
}
