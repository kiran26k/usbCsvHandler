package com.ubs.exceptions;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonErrorMessage {

	int value;
	Date date;
	String message;
	String description;

	public CommonErrorMessage(int value, Date date, String message, String description) {
		this.value = value;
		this.date = date;
		this.message = message;
		this.description = description;
	}

}
