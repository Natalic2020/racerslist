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
	
	public List<RacerData> parseFileToListRacerData(String file) {	
		List<RacerData> racerDataList;
		try (Stream<String> fileInStream = Files.lines(Paths.get(file))) {
			racerDataList = fileInStream
					        .map(temp ->
			                     new RacerData(temp))
					        .collect(Collectors.toList());
		} catch (IOException e) {
			racerDataList = new ArrayList<>();
			e.printStackTrace();
		}
		return racerDataList;
	}
	
	public Map<String, String> parseFileToMap(String file) {
		Map<String, String> mapAbbreviations = new HashMap<>();
		try (Stream<String> fileInStream = Files.lines(Paths.get(file))) {
			mapAbbreviations = fileInStream
					.collect(Collectors.toMap(i -> i.substring(0, 3), i -> i.substring(3)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapAbbreviations;
	}	
	
	public Duration parseStringToDuration(String timeStart, String timeEnd) {
		return Duration.between(parseStringToLocalDT(timeStart), parseStringToLocalDT(timeEnd));
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
