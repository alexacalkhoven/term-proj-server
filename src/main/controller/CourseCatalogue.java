package main.controller;
import java.util.ArrayList;

import main.model.Course;
import main.model.CourseOffering;

public class CourseCatalogue
{
	private ArrayList<Course> courseList;

	public CourseCatalogue()
	{
		loadFromDatabase();
	}

	public Course searchCourse(String courseName, int courseNum)
	{
		Course course = getCourse(courseName, courseNum);

		if (course == null)
		{
			System.err.println("Error, course not found: " + courseName + " " + courseNum);
		}

		return course;
	}

	public Course getCourse(String courseName, int courseNum)
	{
		for (Course c : courseList)
		{
			if (c.getCourseName().contentEquals(courseName) && c.getCourseNum() == courseNum)
			{
				return c;
			}
		}

		return null;
	}

	public void createCourseOffering(Course c, int secNum, int secCap)
	{
		if (c == null)
		{
			System.err.println("Error, cannot create offering for null course");
			return;
		}
		
		if (secNum <= 0)
		{
			System.err.println("Error, cannot create offering with section number < 0");
			return;
		}
		
		if (secCap < 0)
		{
			System.err.println("Error, cannot create offering with section capacity < 0");
			return;
		}
		
		if (c.getCourseOffering(secNum) != null)
		{
			System.err.println("Error, this section number already exists");
			return;
		}

		CourseOffering offering = new CourseOffering(secNum, secCap);
		c.addOffering(offering);
		
		System.out.println("Created course offering for " + c.getFullName() + ":");
		System.out.println(offering);
	}

	public void createCourse(String name, int num)
	{
		Course course = getCourse(name, num);

		if (course != null)
		{
			System.err.println("Error, this course already exists");
			return;
		}

		courseList.add(new Course(name, num));
		System.out.println("Created course: " + name + " " + num);
	}

	public void removeCourse(Course course)
	{
		if (course == null) return;

		courseList.remove(course);
		System.out.println("Removed course: " + course.getCourseName() + " " + course.getCourseNum());
	}

	public ArrayList<Course> getCourses()
	{
		return courseList;
	}

	public String toString()
	{
		String s = "\nAll courses in the catalogue:\n";

		for (Course c : courseList)
		{
			s += c + "\n";
		}

		s += "----------------\n";

		return s;
	}

	// Typically, methods called from other class methods are private and are not
	// exposed for use by other classes. Referred to as helper methods or utility
	// methods.
	private void loadFromDatabase()
	{
		DBManager db = new DBManager();
		courseList = db.readFromDataBase();
	}
}
