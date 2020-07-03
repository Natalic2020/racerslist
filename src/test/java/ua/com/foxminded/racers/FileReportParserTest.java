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
				fileReportParser.checkFile(absolutePathStart);
			});
	}

	@Test
	public void formRacersList_shouldThrowIllegalArgumentException_whenInputEmptyString() {
		final String absolutePathStart = "";
		assertThrows(IllegalArgumentException.class, () ->
			{
				fileReportParser.checkFile(absolutePathStart);
			});
	}
	
	@Test
	public void parseFileToListRacerData_shouldReadNameAndCar_whenInputRightFile() throws FileNotFoundException {
		final String fileName = "abbr_test.txt";
		
		List<RacerData> actual =  fileReportParser.parseFileToListRacerData(fileName);
		String expected = "[Lewis Hamilton     | MERCEDES                      | 0]";
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void parseFileToListRacerData_shouldReturnEmptyArray_whenInputEmptyFile() throws FileNotFoundException {
		
		String fileName = "empty.txt";	
		
		List<RacerData> actual =  fileReportParser.parseFileToListRacerData(fileName);
		String expected = "[]";
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void parseFileToListRacerData_shouldThrowFileNotFoundException_whenInputnotExistFile() {
		final String fileName = "not_file.txt";
		
		assertThrows(FileNotFoundException.class, () ->
		{
			fileReportParser.parseFileToListRacerData(fileName);
		});
	}
	
	@Test
	public void parseRacer_shouldNameAndCar_whenInputRightText() {
		String text = "LHM_Lewis Hamilton_MERCEDES";
		RacerData actual = fileReportParser.parseRacer(text);
		String expected = "Lewis Hamilton     | MERCEDES                      | 0";
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void parseRacer_shouldReturnEmptyNameAndCar_whenInputTextWithoutOneSeparator() {
		String text = "LHM_Lewis Hamilton";
		RacerData actual = fileReportParser.parseRacer(text);
		String expected = "                   |                               | 0";
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void parseFileToMap_shouldLocalDateTime_whenInputFileWithRightFormatedDataTime() throws FileNotFoundException {
		final String fileName = "time_test.txt";
		
		Map<String, LocalDateTime> actual =  fileReportParser.parseFileToMap(fileName);
		String expected = "{SVF=2018-05-24T12:02:58.917}";
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void parseFileToMap_shouldThrowException_whenInputFileWithBrokenDataTime() throws FileNotFoundException {
		final String fileName = "time_brocken.txt";
		
		assertThrows(DateTimeParseException.class, () ->
		{
			fileReportParser.parseFileToMap(fileName);
		});
	}
}
