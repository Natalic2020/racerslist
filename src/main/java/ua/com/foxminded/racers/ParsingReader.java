package ua.com.foxminded.racers;

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
	
	public Duration parseStringToDuration(String time) {
		String timeStart = time.substring(0, 23);
		String timeEnd = time.substring(23);
		return Duration.between(parseStringToLocalDT(timeStart), parseStringToLocalDT(timeEnd));
	}

	private LocalDateTime parseStringToLocalDT(String text) {
		return LocalDateTime.parse(text.replace('_', ' '), DateTimeFormatter.ofPattern(PATTERN_DATA_TIME));
	}
}
