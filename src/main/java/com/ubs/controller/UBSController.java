package com.ubs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ubs.dto.UserDto;
import com.ubs.service.UserService;
import com.ubs.utility.CsvUtility;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class UBSController {

	@Autowired
	UserService userService;

	@GetMapping("/getUser/{id}")
	@Description("API to get user by its primary key")
	public ResponseEntity<UserDto> getPersistedData(@PathVariable("id") long primaryKey) {
		UserDto userData = userService.getUserData(primaryKey);
		log.info("user get data success" + primaryKey);
		return new ResponseEntity<>(userData, HttpStatus.OK);
	}

	@GetMapping("/getUserList")
	@Description("API to fetch userList")
	public ResponseEntity<List<UserDto>> getPersistedDataList() {
		List<UserDto> userList = userService.getUserList();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	@DeleteMapping("/deleteUser/{id}")
	@Description("API to delete user by primaryKey")
	public ResponseEntity<String> delete(@PathVariable("id") long primaryKey) {
		Long deletedId = userService.deleteUserData(primaryKey);
		log.info("user delete success " + deletedId);
		return new ResponseEntity<>("User Deleted " + deletedId, HttpStatus.OK);
	}

	@PostMapping("/upload-csv-file")
	@Description("API to upload Multipart File")
	public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file) {
		if (CsvUtility.isCSVFormat(file)) {
			try {
				userService.updalodCsvData(file);
				log.info("File Upload Success");
				return new ResponseEntity<>("File Uplaod Successfully", HttpStatus.OK);
			} catch (Exception e) {
				log.error("CSV File Upload failed " + e.getMessage());
				return new ResponseEntity<>("File Uplaod Failed", HttpStatus.EXPECTATION_FAILED);
			}
		}
		log.info("File Format should be CSV");
		return new ResponseEntity<>("Please Use CSV Format", HttpStatus.BAD_REQUEST);
	}

}
