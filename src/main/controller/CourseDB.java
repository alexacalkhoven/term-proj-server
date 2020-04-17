package main.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.model.Course;
import main.model.CourseOffering;
import main.model.Registration;
import main.model.Student;

public class CourseDB {

	DBManager db;
	int courseId;

	public CourseDB(DBManager db, int courseId) {
		this.db = db;
		this.courseId = courseId;
	}

	public ArrayList<Course> getPreReqs() {
		ResultSet res = db.query("SELECT * FROM prerequisites WHERE course_id=?", courseId);

		ArrayList<Course> preReqs = new ArrayList<Course>();
		try {
			while (res.next()) {
				String name = res.getString(2);
				int number = res.getInt(3);

				Course course = new Course(name, number);
				course.setId(res.getInt(1));
				preReqs.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return preReqs;
	}

	public ArrayList<CourseOffering> getOfferings() {
		ResultSet res = db.query("SELECT * FROM offerings WHERE course_id=?", courseId);

		ArrayList<CourseOffering> offerings = new ArrayList<CourseOffering>();

		try {
			while (res.next()) {
				int secNum = res.getInt(1);
				int secCap = res.getInt(2);
				int id = res.getInt(5);

				CourseOffering off = new CourseOffering(secNum, secCap, id);

				// off.setCourse(course);?

				ResultSet student = db.query("SELECT * FROM registrations WHERE offering_id=?", id);
				while (student.next()) {
					Registration reg = new Registration();
					reg.setOffering(off);
					reg.setStudent(new Student(student.getString(2), student.getInt(1)));
					off.addRegistration(reg);
				}
				// set student list
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return offerings;
	}
}
