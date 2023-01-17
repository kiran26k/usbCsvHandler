package com.ubs.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ubs.dto.UserDto;
import com.ubs.exceptions.UserNotFoundException;

public interface UserService {

	UserDto getUserData(Long primaryKey) throws UserNotFoundException;

	Long deleteUserData(Long primaryKey);

	void updalodCsvData(MultipartFile file);
	
	List<UserDto> getUserList();

}
