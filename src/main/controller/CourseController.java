package main.controller;

import java.util.ArrayList;

import main.model.CourseCatalogue;

public class CourseController {
	private CommunicationManager comManager;
	private CourseCatalogue courseCatalogue;
	
	public CourseController(CommunicationManager comManager) {
		this.comManager = comManager;
		this.courseCatalogue = new CourseCatalogue();
		comManager.registerHandlerClass(this);
	}
	
	@HandleRequest("with-args")
	public void takeMyStrangs(ArrayList<String> strangs) {
		for (String s : strangs) {
			System.out.println(s);
		}
	}
}
