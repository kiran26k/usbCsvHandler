package com.ubs.utility;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.ubs.dto.UserDto;

public class DataValidator {

	public static boolean isValidISODateTime(String date) {
		try {
			DateTimeFormatter.ISO_DATE_TIME.parse(date);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public static boolean isValidPrimaryKey(String key) {
		if (key != null && !key.isEmpty()) {
			return true;
		}
		return false;

	}

	public static boolean validateUser(UserDto user) {
		if (isValidPrimaryKey(user.getPrimaryKey()) && isValidISODateTime(user.getUdated_timeStamp())) {
			return true;
		}
		return false;
	}
}
