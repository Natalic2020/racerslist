package ua.com.foxminded.racers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParsingReader {
	
private static final String PATTERN_DATA_TIME = "yyyy-MM-dd HH:mm:ss.SSS";
	
private static final String TEXT_SEPARATOR = "_";

	public List<RacerData> parseFileToListRacerData(String fileName) {
		String file = reseivePath(fileName);
		List<RacerData> racerDataList;
		try (Stream<String> fileInStream = Files.lines(Paths.get(file))) {
			racerDataList = fileInStream
					        .map(text ->
			                     new RacerData(text.substring(0,3), parseName(text), parseCar(text)))
					        .collect(Collectors.toList());
		} catch (IOException e) {
			racerDataList = new ArrayList<>();
			e.printStackTrace();
		}
		return racerDataList;
	}
	
	private String parseName(String text) {
		int indexSeparator = text.indexOf(TEXT_SEPARATOR, 5);
		return text.substring(4,indexSeparator);
	}
	
	private String parseCar(String text) {
		int indexSeparator = text.indexOf(TEXT_SEPARATOR, 5);
		return text.substring(indexSeparator + 1);
	}
	
	public Map<String, LocalDateTime> parseFileToMap(String fileName) {
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
		return Duration.between(timeStart,timeEnd);
	}

	private LocalDateTime parseStringToLocalDT(String text) {
		return LocalDateTime.parse(text.replace('_', ' '), DateTimeFormatter.ofPattern(PATTERN_DATA_TIME));
	}
	
	private void checkFile(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("Null parameters are not allowed " + fileName);
		}
		if (fileName.isEmpty()) {
			throw new IllegalArgumentException("Null parameters are not allowed "  + fileName);
		}	
	}
	
	public String reseivePath(String file) {
	    checkFile(file);
	    String relativePath = getClass().getClassLoader().getResource(file).getPath().substring(1);
	    File f = new File(relativePath);
		if (!f.isFile()) {
			throw new IllegalArgumentException("Directory is  not allowed  "  + relativePath + " Wait for a file ....");
		} 
		return relativePath;
	}
}
