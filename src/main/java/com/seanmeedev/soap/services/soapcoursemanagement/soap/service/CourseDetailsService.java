package com.seanmeedev.soap.services.soapcoursemanagement.soap.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.seanmeedev.soap.services.soapcoursemanagement.soap.bean.Course;

@Component
public class CourseDetailsService {
	
	private static List<Course> courses = new ArrayList<>();
	
	static {
		Course course1 = new Course(1, "Spring", "10 Steps");
		courses.add(course1);
		
		Course course2 = new Course(2, "Spring MVC", "10 Examples");
		courses.add(course2);
		
		Course course3 = new Course(3, "Spring Boot", "6k Students");
		courses.add(course3);
		
		Course course4 = new Course(4, "Maven", "Most popular maven course on internet");
		courses.add(course4);		
	}
	
	//course - 1
	public Course findById(int id) {
		for(Course course:courses) {
			if(course.getId()==id)
				return course;
		}
		return null;
	}
	
	//courses
	public List<Course> findAll() {
		return courses;
	}
	
	//deletecourse
	public boolean deleteById(int id) {
		
		Iterator<Course> iterator = courses.iterator();
		
		while(iterator.hasNext()) {
			Course course = iterator.next();
			if(course.getId() == id) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}
	
	//updating course & new course
}
