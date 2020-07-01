package ua.com.foxminded.racers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReportParser {

	private static final String PATTERN_DATA_TIME = "yyyy-MM-dd HH:mm:ss.SSS";

	private static final String TEXT_SEPARATOR = "_";

	public List<RacerData> parseFileToListRacerData(String fileName) throws FileNotFoundException {
		String file = reseivePath(fileName);
		List<RacerData> racerDataList;
		try (Stream<String> fileInStream = Files.lines(Paths.get(file))) {
			racerDataList = fileInStream
			        .map(this::parseRacer)
			        .collect(Collectors.toList());
		} catch (IOException e) {
			racerDataList = new ArrayList<>();
			e.printStackTrace();
		}
		return racerDataList;
	}

	public RacerData parseRacer (String text) {
		return new RacerData(text.substring(0, 3), parseName(text), parseCar(text));
	}
	
	private String parseName(String text) {
		int indexSeparator = text.indexOf(TEXT_SEPARATOR, 5);
		if (indexSeparator==-1) {
			return null;
		}
		return text.substring(4, indexSeparator);
	}

	private String parseCar(String text) {
		int indexSeparator = text.indexOf(TEXT_SEPARATOR, 5);
		if (indexSeparator==-1) {
			return null;
		}
		return text.substring(indexSeparator + 1);
	}

	public Map<String, LocalDateTime> parseFileToMap(String fileName) throws FileNotFoundException {
		String file = reseivePath(fileName);
		Map<String, LocalDateTime> mapAbbreviations = new HashMap<>();
		try (Stream<String> fileInStream = Files.lines(Paths.get(file))) {
			mapAbbreviations = fileInStream
			        .collect(Collectors.toMap(i -> i.substring(0, 3), i -> parseStringToLocalDT(i.substring(3))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapAbbreviations;
	}

	public Duration parseStringToDuration(LocalDateTime timeStart, LocalDateTime timeEnd) {
		if (timeStart == null || timeEnd == null) {
			return Duration.ZERO;
		}
		return Duration.between(timeStart, timeEnd);
	}

	private LocalDateTime parseStringToLocalDT(String text) {
		LocalDateTime dateTime = null ;
		
		try {
			dateTime = LocalDateTime.parse(text.replace('_', ' '), DateTimeFormatter.ofPattern(PATTERN_DATA_TIME));
		}catch (DateTimeParseException e){
			e.printStackTrace();	
		}
		return dateTime;
	}

	private void checkFile(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("Null parameters are not allowed " + fileName);
		}
		if (fileName.isEmpty()) {
			throw new IllegalArgumentException("Null parameters are not allowed " + fileName);
		}
	}

	private String reseivePath(String fileName) throws FileNotFoundException {
		checkFile(fileName);	
		ClassLoader classLoader = getClass().getClassLoader();
		if (classLoader.getResource(fileName) == null) {
			throw new FileNotFoundException("File was not present: " + fileName);
		}
		File file = new File(classLoader.getResource(fileName).getFile());
	
		if (!file.isFile()) {
			throw new IllegalArgumentException("Directory is  not allowed  " + file.getAbsolutePath() + " Wait for a file ....");
		}
		String absolutePath = file.getAbsolutePath();
		
		return absolutePath;
	}
}
