package ua.com.foxminded.racers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReportParser {

	private static final String PATTERN_DATA_TIME = "yyyy-MM-dd HH:mm:ss.SSS";

	private static final char TEXT_SEPARATOR = '_';
	private static final String PARSING_EMPTY_RESULT = "";
	public static final int NOT_FOUND_INDEX = -1;
	public static final char EMPTY_CHAR = ' ';
	public static final int LENGTH_ABBR = 3;
	public static final int ABBR_START_INDEX = 0;
	public static final int NAME_START_INDEX = 4;

	public List<RacerData> parseRacerData(String fileName) {
		String file = receivePath(fileName);
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
		return new RacerData(parseAbbreviation(text), parseName(text), parseCar(text));
	}

	private String parseAbbreviation(String text) {
		return text.substring(ABBR_START_INDEX, LENGTH_ABBR);
	}

	private String parseTime(String text) {
		return text.substring(LENGTH_ABBR);
	}
	
	private String parseName(String line) {
		int indexSeparator = findIndexOfElementInLine(line);
		return indexSeparator == NOT_FOUND_INDEX ? PARSING_EMPTY_RESULT : line.substring(NAME_START_INDEX, indexSeparator);
	}

	private String parseCar(String line) {
		int indexSeparator = findIndexOfElementInLine(line);
		return indexSeparator == NOT_FOUND_INDEX ? PARSING_EMPTY_RESULT : line.substring(indexSeparator + 1);
	}

	private int findIndexOfElementInLine(String line) {
		return line.indexOf(TEXT_SEPARATOR, NAME_START_INDEX);

	}

	public Map<String, LocalDateTime> parseFileToMap(String fileName) {
		String file = receivePath(fileName);
		Map<String, LocalDateTime> mapAbbreviations = new HashMap<>();
		try (Stream<String> fileInStream = Files.lines(Paths.get(file))) {
			mapAbbreviations = fileInStream
			        .collect(Collectors.toMap(i -> parseAbbreviation(i), i -> parseStringToLocalDT(parseTime(i))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapAbbreviations;
	}

	public Duration recieveDuration(LocalDateTime timeStart, LocalDateTime timeEnd) {
		if (timeStart==null || timeEnd==null) {
			return Duration.ZERO;
		}
		return Duration.between(timeStart, timeEnd);
	}

	private LocalDateTime parseStringToLocalDT(String text) {
		LocalDateTime	dateTime = LocalDateTime.parse(text.replace(TEXT_SEPARATOR, EMPTY_CHAR),
				DateTimeFormatter.ofPattern(PATTERN_DATA_TIME));
		return dateTime;
	}

	public void checkFileName(String fileName) {
		Optional.ofNullable(fileName).orElseThrow(()-> new IllegalArgumentException("Null as file name is not allowed "));
		
		if (fileName.isEmpty()) {
			throw new IllegalArgumentException("Empty parameters are not allowed " + fileName);
		}
	}

	private String receivePath(String fileName) {
		checkFileName(fileName);

		ClassLoader classLoader = getClass().getClassLoader();

		File file = Optional
				.ofNullable(classLoader.getResource(fileName))
				.map(URL::getFile)
				.map(File::new)
				.orElseThrow(() -> new IllegalArgumentException("File doesn't exis"));

		checkFile(file);

		return file.getAbsolutePath();
	}

	private void checkFile(File file) {
		if (isNotExist(file)) {
			throw new IllegalArgumentException("File " + file + " doesn't exist");
		}
		if (isNotFile(file)) {
			throw new IllegalArgumentException("Directory is  not allowed  " + file + " Wait for a file ....");
		}
	}

	private boolean isNotFile(File file) {
		return !file.isFile();
	}

	private boolean isNotExist(File file) {
		return !file.exists();
	}
}
