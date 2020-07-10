package ua.com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReportParserTest {

	private static final String PATTERN_DATA_TIME = "yyyy-MM-dd HH:mm:ss.SSS";
	FileReportParser fileReportParser	= new FileReportParser();
	
	@Test 
	public void checkFileName_shouldThrowIllegalArgumentException_whenInputNull() {
		final String absolutePathStart = null;
		assertThrows(IllegalArgumentException.class, () ->
			{
				fileReportParser.checkFileName(absolutePathStart);
			});
	}

	@Test
	public void checkFileNamet_shouldThrowIllegalArgumentException_whenInputEmptyString() {
		final String absolutePathStart = "";
		assertThrows(IllegalArgumentException.class, () ->
			{
				fileReportParser.checkFileName(absolutePathStart);
			});
	}
	
	@Test
	public void parseRacersData_shouldReadNameAndCar_whenInputCorrectFile(){
		final String fileName = "abbr_test.txt";
		String expected = "[Lewis Hamilton     | MERCEDES                      | 0]";
		
		List<RacerData> actual =  fileReportParser.parseRacersData(fileName);
		
		assertEquals(expected, actual.toString());
	}

	@Test
	public void readFileToLines_shouldReadFileToList_whenInputCorrect() {
		final String fileName = "abbr_test.txt";
		List<String> expected = new ArrayList<>();
		expected.add("LHM_Lewis Hamilton_MERCEDES");

		List<String> actual =  fileReportParser.readFileToLines(fileName);

		assertEquals(expected, actual);
	}

	@Test 
	public void readFileToLines_shouldThrowIllegalArgumentException_whenInputFileIsNotValid() {
		final String fileName = "not_file.txt";
		assertThrows(IllegalArgumentException.class, () ->
		{
			fileReportParser.readFileToLines(fileName);
		});
	}

	@Test 
	public void parseRacersRawData_shouldReturnListOfRacers_whenInputValid() {
		List<RacerData> expected = new ArrayList<>();
		expected.add(new RacerData("LHM", "Lewis Hamilton", "MERCEDES"));
		
		List<String> rawData = new ArrayList<>();
		rawData.add("LHM_Lewis Hamilton_MERCEDES");
		
		List<RacerData> actual = fileReportParser.parseRacersRawData(rawData);
		
		assertEquals(expected, actual);
	}

	@Test 
	public void parseRacersData_shouldThrowFileNotFoundException_whenInputnotExistFile() {
		final String fileName = "not_file.txt";
		assertThrows(IllegalArgumentException.class, () ->
		{
			fileReportParser.parseRacersData(fileName);
		});
	}
	
	@Test 
	public void parseRacer_shouldReturnInstanceOfRacerData_whenInputRightText() {
		String text = "LHM_Lewis Hamilton_MERCEDES";
		RacerData expected = new RacerData("LHM", "Lewis Hamilton", "MERCEDES");
		
		RacerData actual = fileReportParser.parseRacer(text);
		
		assertEquals(expected, actual);
	}
	
	@Test 
	public void parseRacer_shouldReturnInstanceOfRacerData_whenInputTextWithoutOneSeparator() {
		String text = "LHM_Lewis Hamilton";
		RacerData expected = new RacerData("LHM", "", "");
		
		RacerData actual = fileReportParser.parseRacer(text);
		
		assertEquals(expected, actual);
	}
	
	@Test 
	public void parseTimeData_shouldReturnHashMap_whenInputFileWithRightFormatedDataTime() {
		final String fileName = "time_test.txt";
		Map<String, LocalDateTime> expected = new HashMap<>();
		
		expected.put("SVF", LocalDateTime.parse("2018-05-24 12:02:58.917",
				DateTimeFormatter.ofPattern(PATTERN_DATA_TIME)));
		
		Map<String, LocalDateTime> actual =  fileReportParser.parseTimeData(fileName);
		
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void parseTimeRawData_shouldReturnMapTime_whenInputIsValid() {
		Map<String, LocalDateTime> expected = new HashMap<>();
		expected.put("SVF", LocalDateTime.parse("2018-05-24 12:02:58.917",
				DateTimeFormatter.ofPattern(PATTERN_DATA_TIME)));
		
		List<String> rawData = new ArrayList<>();
		rawData.add("SVF2018-05-24_12:02:58.917");
		
		Map<String, LocalDateTime> actual = fileReportParser.parseTimeRawData(rawData);
		
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void parseTimeRawData_shouldThrowDateTimeParseException_whenInputWithoutAbbreviation() {
		List<String> rawData = new ArrayList<>();
		rawData.add("2018-05-24_12:02:58.917");
		assertThrows(DateTimeParseException.class, () ->
		{
			fileReportParser.parseTimeRawData(rawData);
		});
	}

	@Test
	public void parseTimeRawData_shouldThrowDateTimeParseException_whenInputWithoutDate() {
		List<String> rawData = new ArrayList<>();
		rawData.add("SVF12:02:58.917");
		assertThrows(DateTimeParseException.class, () ->
		{
			fileReportParser.parseTimeRawData(rawData);
		});
	}

	@Test
	public void parseTimeRawData_shouldThrowDateTimeParseException_whenInputWithoutTime() {
		List<String> rawData = new ArrayList<>();
		rawData.add("SVF2018-05-24");
		assertThrows(DateTimeParseException.class, () ->
		{
			fileReportParser.parseTimeRawData(rawData);
		});
	}

	@Test
	public void receivePath_shouldThrowIllegalArgumentException_whenInputFileWithBrokenDataTime() {
		final String fileName = "time_brocken.txt";
		assertThrows(IllegalArgumentException.class, () ->
		{
			fileReportParser.receivePath(fileName);
		});
	}
}
