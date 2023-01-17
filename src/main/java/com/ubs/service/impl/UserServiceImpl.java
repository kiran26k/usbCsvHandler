package com.ubs.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ubs.dto.UserDto;
import com.ubs.entity.User;
import com.ubs.exceptions.UserNotFoundException;
import com.ubs.repo.UserRepository;
import com.ubs.service.UserService;
import com.ubs.utility.CsvUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	@Override
	public UserDto getUserData(Long primaryKey) {
		Optional<User> optionalUser = userRepo.findByPrimaryKey(primaryKey);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return UserDto.builder().primaryKey(user.getPrimaryKey().toString()).description(user.getDescription())
					.name(user.getName()).udated_timeStamp(user.getUdatedTimeStamp()).build();
		}
		log.info("User not found for the id " + primaryKey);
		optionalUser.orElseThrow(() -> new UserNotFoundException("User not found for id " + primaryKey));
		return null;
	}

	@Override
	@Transactional
	public Long deleteUserData(Long primaryKey) {
		Long deleted = userRepo.deleteByPrimaryKey(primaryKey);
		if (deleted == 0) {
			log.info("Cant delete, User not found with id " + primaryKey);
			throw new UserNotFoundException("Cant delete, User not found with id " + primaryKey);
		}
		return deleted;
	}

	@Override
	public void updalodCsvData(MultipartFile file) {
		try {
			List<UserDto> userDtoList = CsvUtility.readCSVDataFromFile(file.getInputStream());
			List<User> userList = userDtoList.stream()
					.map(userDto -> User.builder().primaryKey(Long.parseLong(userDto.getPrimaryKey()))
							.name(userDto.getName()).description(userDto.getDescription())
							.udatedTimeStamp(userDto.getUdated_timeStamp()).build())
					.collect(Collectors.toList());
			userRepo.saveAll(userList);
			log.info("Uploaded data saved successfully ");
		} catch (IOException e) {
			log.error("fail to save csv data to db");
			throw new RuntimeException("fail to save csv data: " + e.getMessage());
		}
	}

	@Override
	public List<UserDto> getUserList() {
		List<User> users = userRepo.findAll();
		return users.stream()
				.map(user -> UserDto.builder().primaryKey(user.getPrimaryKey().toString())
						.description(user.getDescription()).name(user.getName())
						.udated_timeStamp(user.getUdatedTimeStamp()).build())
				.collect(Collectors.toList());
	}
}
