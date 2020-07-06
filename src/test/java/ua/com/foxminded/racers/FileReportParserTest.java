package ua.com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class FileReportParserTest {

	FileReportParser fileReportParser	= new FileReportParser();
	
	@Test
	public void formRacersList_shouldThrowIllegalArgumentException_whenInputNull() {
		final String absolutePathStart = null;
		assertThrows(IllegalArgumentException.class, () ->
			{
				fileReportParser.checkFileName(absolutePathStart);
			});
	}

	@Test
	public void formRacersList_shouldThrowIllegalArgumentException_whenInputEmptyString() {
		final String absolutePathStart = "";
		assertThrows(IllegalArgumentException.class, () ->
			{
				fileReportParser.checkFileName(absolutePathStart);
			});
	}
	
	@Test
	public void parseFileToListRacerData_shouldReadNameAndCar_whenInputRightFile() throws FileNotFoundException {
		final String fileName = "abbr_test.txt";
		List<RacerData> actual =  fileReportParser.parseRacerData(fileName);
		String expected = "[Lewis Hamilton     | MERCEDES                      | 0]";
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void parseFileToListRacerData_shouldReturnEmptyArray_whenInputEmptyFile() {
		String fileName = "empty.txt";	
		List<RacerData> actual =  fileReportParser.parseRacerData(fileName);
		String expected = "[]";
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void parseFileToListRacerData_shouldThrowFileNotFoundException_whenInputnotExistFile() {
		final String fileName = "not_file.txt";
		
		assertThrows(IllegalArgumentException.class, () ->
		{
			fileReportParser.parseRacerData(fileName);
		});
	}
	
	@Test 
	public void parseRacer_shouldReturnInstanceOfRacerData_whenInputRightText() {
		String text = "LHM_Lewis Hamilton_MERCEDES";
		RacerData actual = fileReportParser.parseRacer(text);
		assertEquals("ua.com.foxminded.racers.RacerData", actual.getClass().getName());
	}
	
	@Test
	public void parseRacer_shouldReturnInstanceOfRacerData_whenInputTextWithoutOneSeparator() {
		String text = "LHM_Lewis Hamilton";
		RacerData actual = fileReportParser.parseRacer(text);
		assertEquals("ua.com.foxminded.racers.RacerData", actual.getClass().getName());
	}
	
	@Test
	public void parseFileToMap_shouldReturnHashMap_whenInputFileWithRightFormatedDataTime() {
		final String fileName = "time_test.txt";
		Map<String, LocalDateTime> actual =  fileReportParser.parseFileToMap(fileName);
		assertEquals("java.util.HashMap", actual.getClass().getName());
	}
	
	public void parseFileToMap_shouldThrowException_whenInputFileWithBrokenDataTime() {
		final String fileName = "time_brocken.txt";
		
		assertThrows(DateTimeParseException.class, () ->
		{
			fileReportParser.parseFileToMap(fileName);
		});
	}
}
