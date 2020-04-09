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
	
	@HandleRequest("no-args")
	public void func() {
		System.out.println("it works");
	}
	
	@HandleRequest("with-args")
	public void testFunc(ArrayList<String> strings) {
		for (String s : strings) {
			System.out.println(s);
		}
	}
	
	@HandleRequest("return")
	public String ret() {
		return "omg";
	}
}
