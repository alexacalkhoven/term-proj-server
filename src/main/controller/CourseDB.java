package main.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.model.Course;
import main.model.CourseOffering;

public class CourseDB {

	private DBManager db;
	private int courseId;

	public CourseDB(DBManager db, int courseId) {
		this.db = db;
		this.courseId = courseId;
	}
	
	public Course getCourse() {
		ResultSet res = db.query("SELECT * FROM courses WHERE id=?", courseId);
		Course course = null;
		
		try {
			if (res.next()) {
				course = new Course(res.getString(2), res.getInt(2));
				course.setCourseId(courseId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return course;
	}

	public ArrayList<Course> getPreReqs() {
		ArrayList<Course> preReqs = new ArrayList<Course>();
		ResultSet res = db.query("SELET courses.id, courses.name, courses.number FROM prerequisites "
				+ "INNER JOIN courses ON prerequisites.child_id=courses.id AND prerequisites.parent_id=?", courseId);
		
		try {
			while (res.next()) {
				int id = res.getInt(1);
				String name = res.getString(2);
				int number = res.getInt(3);

				Course course = new Course(name, number);
				course.setCourseId(id);
				preReqs.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return preReqs;
	}
	
	public ArrayList<CourseOffering> getOfferings(int courseId) {
		ArrayList<CourseOffering> offerings = new ArrayList<CourseOffering>();
		ResultSet res = db.query("SELECT offerings.id, offerings.number, offerings.capacity, offerings.students, courses.* FROM "
				+ "offerings INNER JOIN courses ON offerings.course_id=courses.id AND course_id=?;", courseId);

		try {
			while (res.next()) {
				int offeringId = res.getInt(1);
				int secNum = res.getInt(2);
				int secCap = res.getInt(3);
				int studentAmount = res.getInt(4);

				CourseOffering off = new CourseOffering(secNum, secCap);
				off.setOfferingId(offeringId);
				off.setStudentAmount(studentAmount);
				
				String courseName = res.getString(6);
				int courseNumber = res.getInt(7);
				
				Course course = new Course(courseName, courseNumber);
				course.setCourseId(courseId);
				
				off.setCourse(course);				
				offerings.add(off);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return offerings;
	}
}
