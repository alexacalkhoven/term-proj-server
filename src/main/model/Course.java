package main.model;

import java.util.ArrayList;

public class Course
{
	private String courseName;
	private int courseNum;
	private ArrayList<Course> preReq;
	private ArrayList<CourseOffering> offeringList;
	
	public Course(String name, int num)
	{
		setCourseName(name);
		setCourseNum(num);
		
		// Not aggregation, since you're not actually creating any preReqs
		// Only creating a container for preReqs
		// Both of the following are only association
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
	}
	
	public void addPreReq(Course course)
	{
		preReq.add(course);
	}
	
	public void addOffering(CourseOffering offering)
	{
		if (offering == null) return;
		
		// Both objects are set, marking the association
		offering.setCourse(this);
		offeringList.add(offering);
	}
	
	public CourseOffering getCourseOffering(int section)
	{
		for (CourseOffering o : offeringList)
		{
			if (o.getSecNum() == section) return o;
		}
		
		return null;
	}
	
	public CourseOffering searchOffering(int section)
	{
		CourseOffering offering = getCourseOffering(section);
		
		if (offering == null)
		{
			System.err.println("Error, section not found: " + section);
		
		}
		
		return offering;
	}

	public String getCourseName()
	{
		return courseName;
	}

	public void setCourseName(String courseName)
	{
		this.courseName = courseName;
	}

	public int getCourseNum()
	{
		return courseNum;
	}

	public void setCourseNum(int courseNum)
	{
		this.courseNum = courseNum;
	}
	
	public String getFullName()
	{
		return getCourseName() + " " + getCourseNum();
	}
	
	public String toString()
	{
		String s = "Course: " + getCourseName() + " " + getCourseNum() + "\nAll course sections:\n";
		
		for (CourseOffering c : offeringList)
		{
			s += c + "\n";
		}
		
		return s;
	}
}
