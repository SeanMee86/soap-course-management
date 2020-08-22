package com.seanmeedev.soap.services.soapcoursemanagement.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{http://seanmeedev.com/courses}001_COURSE_NOT_FOUND")
public class CourseNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8933326727401674541L;

	public CourseNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
