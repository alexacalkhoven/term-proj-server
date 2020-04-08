package main.controller;

import java.util.ArrayList;

import main.model.Course;
import main.model.CourseOffering;

public class DBManager {
	private ArrayList<Course> courseList;

	public DBManager() {
		courseList = new ArrayList<Course>();
	}

	public ArrayList<Course> readFromDataBase() {
		Course engg233 = new Course("ENGG", 233);
		engg233.addOffering(new CourseOffering(1, 100));
		engg233.addOffering(new CourseOffering(2, 50));
		courseList.add(engg233);

		Course ensf409 = new Course("ENSF", 409);
		ensf409.addOffering(new CourseOffering(1, 150));
		courseList.add(ensf409);

		Course phys259 = new Course("PHYS", 259);
		phys259.addOffering(new CourseOffering(1, 80));
		phys259.addOffering(new CourseOffering(2, 90));
		courseList.add(phys259);

		return courseList;
	}
}
