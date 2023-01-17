package com.ubs.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserNotFoundException extends RuntimeException {
	private String message;

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		 super(message);
		 this.message = message;
	}
	
	public UserNotFoundException() {

	}

}