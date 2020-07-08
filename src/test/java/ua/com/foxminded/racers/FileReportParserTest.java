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
	
	@Test // todo test for checkFileName()
	public void formRacersList_shouldThrowIllegalArgumentException_whenInputNull() {
		final String absolutePathStart = null;
		assertThrows(IllegalArgumentException.class, () ->
			{
				fileReportParser.checkFileName(absolutePathStart);
			});
	}

	@Test// todo test for checkFileName()
	public void formRacersList_shouldThrowIllegalArgumentException_whenInputEmptyString() {
		final String absolutePathStart = "";
		assertThrows(IllegalArgumentException.class, () ->
			{
				fileReportParser.checkFileName(absolutePathStart);
			});
	}
	
	@Test // todo test for parseRacersData()
	public void parseFileToListRacerData_shouldReadNameAndCar_whenInputCorrectFile() throws FileNotFoundException {
		final String fileName = "abbr_test.txt";
		List<RacerData> actual =  fileReportParser.parseRacersData(fileName);
		String expected = "[Lewis Hamilton     | MERCEDES                      | 0]";
		assertEquals(expected, actual.toString());
	}
	
	@Test // todo delete, this case test in other method
	public void parseFileToListRacerData_shouldReturnEmptyArray_whenInputEmptyFile() {
		String fileName = "empty.txt";	
		List<RacerData> actual =  fileReportParser.parseRacersData(fileName);
		String expected = "[]";
		assertEquals(expected, actual.toString());
	}

	@Test // todo implement
	public void readFileToLines_shouldReadFileToList_whenInputCorrect() {

	}

	@Test // todo implement
	public void readFileToLines_shouldReturnEmptyList_whenInputFileIsNotValid() {

	}

	@Test // todo implement
	public void parseRacersRawData_shouldReturnListOfRacers_whenInputValid() {

	}

	@Test // todo test for parseRacersData()
	public void parseFileToListRacerData_shouldThrowFileNotFoundException_whenInputnotExistFile() {
		final String fileName = "not_file.txt";
		
		assertThrows(IllegalArgumentException.class, () ->
		{
			fileReportParser.parseRacersData(fileName);
		});
	}
	
	@Test // todo compare objects
	public void parseRacer_shouldReturnInstanceOfRacerData_whenInputRightText() {
		String text = "LHM_Lewis Hamilton_MERCEDES";
		RacerData actual = fileReportParser.parseRacer(text);
		assertEquals("ua.com.foxminded.racers.RacerData", actual.getClass().getName());
	}
	
	@Test //todo compare objects
	public void parseRacer_shouldReturnInstanceOfRacerData_whenInputTextWithoutOneSeparator() {
		String text = "LHM_Lewis Hamilton";
		RacerData actual = fileReportParser.parseRacer(text);
		assertEquals("ua.com.foxminded.racers.RacerData", actual.getClass().getName());
	}
	
	@Test// todo rename, test for parseTimeData(), comapre objects
	public void parseFileToMap_shouldReturnHashMap_whenInputFileWithRightFormatedDataTime() {
		final String fileName = "time_test.txt";
		Map<String, LocalDateTime> actual =  fileReportParser.parseTimeData(fileName);
		assertEquals("java.util.HashMap", actual.getClass().getName());
	}

	@Test
	public void parseTimeRawData_shouldReturnMapTime_whenInputIsValid() {

	}

	@Test// todo rename,  test case when input "2018-05-24_12:02:58.917"
	public void parseTimeRawData_should_whenInputWithoutAbbreviation() {

	}

	@Test// todo rename,  test case when input "12:02:58.917"
	public void parseTimeRawData_should_whenInputWithoutDate() {

	}

	@Test// todo rename,  test case when input "2018-05-24"
	public void parseTimeRawData_should_whenInputWithoutTime() {

	}

		// todo test annotation missed, but anyway it should be tested in another way
	public void parseFileToMap_shouldThrowException_whenInputFileWithBrokenDataTime() {
		final String fileName = "time_brocken.txt";
		
		assertThrows(DateTimeParseException.class, () ->
		{
			fileReportParser.parseTimeData(fileName);
		});
	}
}
