package ua.com.foxminded.racers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RacersList {

	private static final String PATTERN_DATA_TIME = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public Stream<String> formRacersList(String fileStart, String fileEnd, String fileAbbreviations) {
		if (!checkFile(fileStart)) {
			return Stream.empty();
		}
		if (!checkFile(fileEnd)) {
			return Stream.empty();
		}
		if (!checkFile(fileAbbreviations)) {
			return Stream.empty();
		}
		List<RacerData> racerDataList = formRacerList(fileStart, fileEnd, fileAbbreviations);
		return formOutputListRacers(racerDataList);
	}

	private List<RacerData> formRacerList(String fileStartName, String fileEndName, String fileAbbreviationsName) {

		String fileStart = reseivePath(fileStartName);
		String fileEnd = reseivePath(fileEndName);
		String fileAbbreviations = reseivePath(fileAbbreviationsName);

		List<RacerData> racerDataList;
		try ( Stream<String> fileInStream = Files.lines(Paths.get(fileAbbreviations))){
			racerDataList = fileInStream.map(temp -> new RacerData(temp)).collect(Collectors.toList());
		} catch (IOException e) {
			racerDataList = new ArrayList<>(); 
			e.printStackTrace();
		}
		
		Map<String, String> mapStart = convertFileToMap(fileStart);
		Map<String, String> mapEnd = convertFileToMap(fileEnd);
		
		Map<String, String> mapTime = Stream.of(mapStart, mapEnd).flatMap(m -> m.entrySet().stream())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (o1,
						o2) -> o1 + o2));
		
		racerDataList.stream().filter(s -> !mapTime.get(s.getAbbr()).isEmpty()).forEach(s-> 
			s.setRacerTime(reseiveTime(mapTime.get(s.getAbbr())))
			);
		
		racerDataList.sort(Comparator.comparing(RacerData::getRacerTime));
		
		return racerDataList;
	}
	
	private Duration reseiveTime(String time) {
		String timeStart = time.substring(0,23);
		String timeEnd = time.substring(23);
		return Duration.between(convertStringToLocalDT(timeStart), convertStringToLocalDT(timeEnd));
	}
	
	private LocalDateTime convertStringToLocalDT(String text) {
		return LocalDateTime.parse(text.replace('_', ' '), DateTimeFormatter.ofPattern(PATTERN_DATA_TIME));
	}
	
	private Map<String, String> convertFileToMap(String file) {
		Map<String, String> mapAbbreviations = new HashMap<>();
		try ( Stream<String> fileInStream = Files.lines(Paths.get(file))){
			mapAbbreviations = fileInStream
					.collect(Collectors.toMap(i -> i.substring(0, 3), i -> i.substring(3)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapAbbreviations;
	}
	
	private String reseivePath(String file) {
		return getClass().getClassLoader().getResource(file).getPath().substring(1);
	}

	private Stream<String> formOutputListRacers(List<RacerData> racerDataList) {
		Stream<String> first15 = formOutputListRacers(racerDataList, 1, 15);
		Stream<String> separator = Stream.of(String.format("%s%n","*----------------------------------------------------------------"));
		Stream<String> last = formOutputListRacers(racerDataList, 16, racerDataList.size());
		return Stream.concat(Stream.concat(first15, separator), last);
	}

	private Stream<String> formOutputListRacers(List<RacerData> racerDataList, int start, int end) {
		return IntStream.range(start - 1, end).mapToObj(i -> {
			RacerData racer = racerDataList.get(i);
			return String.format("%2d. %s%n", i + 1, racer.toString());
		});
	}

	private boolean checkFile(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("Null parameters are not allowed");
		}
		if (fileName.isEmpty()) {
			throw new IllegalArgumentException("Null parameters are not allowed");
		}
		File f = new File(reseivePath(fileName)); 
		if (f.isFile()) {
			return true;
		}
		return false;
	}
}
