package com.ubs.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<CommonErrorMessage> userNotFoundException(UserNotFoundException ex, WebRequest request) {
		
		  CommonErrorMessage message = new CommonErrorMessage( HttpStatus.NOT_FOUND.value(), new
		  Date(), ex.getMessage(), request.getDescription(false));
		 
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	
	//add all custom exception
	/*
	 * @ExceptionHandler(UserNotFoundException.class) public ResponseEntity<String>
	 * resourceNotFoundException(UserNotFoundException ex, WebRequest request) {
	 * 
	 * ErrorMessage message = new ErrorMessage( HttpStatus.NOT_FOUND.value(), new
	 * Date(), ex.getMessage(), request.getDescription(false));
	 * 
	 * 
	 * return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); }
	 */
}
