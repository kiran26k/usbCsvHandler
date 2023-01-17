package com.ubs.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ubs.dto.UserDto;
import com.ubs.entity.User;
import com.ubs.exceptions.UserNotFoundException;
import com.ubs.repo.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepo;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("Test to find user by PrimaryKey")
	public void getUserDataTest() {
		User user = new User();
		user.setId(1l);
		user.setPrimaryKey(1l);
		user.setName("Kiran");
		when(userRepo.findByPrimaryKey(1l)).thenReturn(Optional.of(user));
		UserDto userDto = userService.getUserData(1l);
		assertEquals(userDto.getName(), user.getName());
	}

	@Test
	@DisplayName("Test for exception of find user")
	public void getUserDataExceptionTest() {
		UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class,
				() -> userService.getUserData(1L));
		assertEquals("User not found for id 1", userNotFoundException.getMessage());
	}

	@Test
	@DisplayName("Test to delete user by PrimaryKey")
	public void deteteUserDataTest() {
		when(userRepo.deleteByPrimaryKey(1l)).thenReturn(1L);
		long count = userService.deleteUserData(1l);
		assertEquals(count, 1l);
	}

	@Test
	@DisplayName("Test for exception of delete user")
	public void deteteUserDataExceptionTest() {
		UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class,
				() -> userService.deleteUserData(1L));
		assertEquals("Cant delete, User not found with id 1", userNotFoundException.getMessage());
	}

	@Test
	@DisplayName("Test to delete user by PrimaryKey")
	public void uploadCSVTest() {
		MockMultipartFile csvfile = new MockMultipartFile("data", "usbData.csv", "text/plain", "some csv".getBytes());
		userService.updalodCsvData(csvfile);
		verify(userRepo, times(1)).saveAll(new ArrayList<User>());
	}

}
