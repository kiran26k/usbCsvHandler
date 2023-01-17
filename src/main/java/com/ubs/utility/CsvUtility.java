package com.ubs.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.ubs.dto.UserDto;

public class CsvUtility {

	private static final String SAMPLE_CSV_FILE_PATH = "src/main/resources/ubsdata.csv";
	public static String TYPE = "text/csv";

	public static void main(String[] args) throws IOException {
		try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
				CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(reader);) {
			for (CSVRecord csvRecord : csvParser) {
				String id = csvRecord.get("PrimaryKey");
				String name = csvRecord.get("Name");
				String description = csvRecord.get("Description");
				String date = csvRecord.get("UdatedTimeStamp");
			}
		}
	}

	public static List<UserDto> readCSVDataFromFile(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			List<UserDto> userDataList = new ArrayList<UserDto>();
			List<CSVRecord> csvRecords = csvParser.getRecords();

			// csvRecords.stream().

			for (CSVRecord csvRecord : csvRecords) {
				UserDto user = UserDto.builder().primaryKey(csvRecord.get("PrimaryKey")).name(csvRecord.get("Name"))
						.description(csvRecord.get("Description")).udated_timeStamp(csvRecord.get("UdatedTimeStamp"))
						.build();
				if (DataValidator.validateUser(user)) {
					userDataList.add(user);
				}
			}
			return userDataList;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

	public static boolean isCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

}
