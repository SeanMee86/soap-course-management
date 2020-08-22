package com.seanmeedev.soap.services.soapcoursemanagement.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.seanmeedev.courses.CourseDetails;
import com.seanmeedev.courses.DeleteCourseDetailsRequest;
import com.seanmeedev.courses.DeleteCourseDetailsResponse;
import com.seanmeedev.courses.GetAllCourseDetailsRequest;
import com.seanmeedev.courses.GetAllCourseDetailsResponse;
import com.seanmeedev.courses.GetCourseDetailsRequest;
import com.seanmeedev.courses.GetCourseDetailsResponse;
import com.seanmeedev.courses.InsertCourseDetailsRequest;
import com.seanmeedev.courses.InsertCourseDetailsResponse;
import com.seanmeedev.soap.services.soapcoursemanagement.soap.bean.Course;
import com.seanmeedev.soap.services.soapcoursemanagement.soap.exception.CourseNotFoundException;
import com.seanmeedev.soap.services.soapcoursemanagement.soap.service.CourseDetailsService;
import com.seanmeedev.soap.services.soapcoursemanagement.soap.service.CourseDetailsService.Status;

@Endpoint
public class CourseDetailsEndpoint {
	
	@Autowired
	CourseDetailsService service;
	
	// method
	// input - GetCourseDetailsRequest
	// output - GetCourseDetailsResponse
	
	// http://in28minutes.com/courses
	// GetCourseDetailsRequest
	
	@PayloadRoot
	(namespace = "http://seanmeedev.com/courses", localPart = "GetCourseDetailsRequest")	
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest
	(@RequestPayload GetCourseDetailsRequest request) {
		Course course = service.findById(request.getId());
		
		if(course ==null)
			throw new CourseNotFoundException("Invalid Course Id: " + request.getId());
		return mapCourseDetails(course);
	}
	
	@PayloadRoot
	(namespace = "http://seanmeedev.com/courses", localPart = "GetAllCourseDetailsRequest")	
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest
	(@RequestPayload GetAllCourseDetailsRequest request) {
		List<Course> courses = service.findAll();
		return mapAllCourseDetails(courses);
	}
	
	@PayloadRoot
	(namespace = "http://seanmeedev.com/courses", localPart = "InsertCourseDetailsRequest")
	@ResponsePayload
	public InsertCourseDetailsResponse processInsertCourseDetailsRequest
	(@RequestPayload InsertCourseDetailsRequest request) {
		InsertCourseDetailsResponse response = new InsertCourseDetailsResponse();
		int id = request.getCourseDetails().getId();
		String name = request.getCourseDetails().getName();
		String description = request.getCourseDetails().getDescription();
		Status status = service.addCourse(new Course(id , name, description));
		response.setStatus(mapStatus(status));
		return response;		
	}

	@PayloadRoot
	(namespace = "http://seanmeedev.com/courses", localPart = "DeleteCourseDetailsRequest")	
	@ResponsePayload
	public DeleteCourseDetailsResponse processDeleteCourseDetailsRequest
	(@RequestPayload DeleteCourseDetailsRequest request) {
		Status status =  service.deleteById(request.getId());
		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		response.setStatus(mapStatus(status));
		return response;
	}

	private com.seanmeedev.courses.Status mapStatus(Status status) {
		if(status==Status.FAILURE)
			return com.seanmeedev.courses.Status.FAILURE;
		return com.seanmeedev.courses.Status.SUCCESS;
	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		response.setCourseDetails(mapCourse(course));
		return response;
	}
	
	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		for(Course course:courses) {
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}
		return response;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setId(course.getId());
		courseDetails.setName(course.getName());
		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}
}
