package ua.com.foxminded.racers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RacersList {

	private static final String SINGLE_INDENT = " ";
	private static final String TEXT_SEPARATOR = "_";
	private static final String TIME_SEPARATOR = "%";
	private static final String PATTERN_DATA_TIME = "yyyy-MM-dd HH:mm:ss.SSS";

	public String formRacersList(String fileStart, String fileEnd, String fileAbbreviations) {
		
		boolean hasFileStart = checkFile(fileStart);
		if (!hasFileStart) {
			return "";
		}
		boolean hasFileEnd = checkFile(fileEnd);
		if (!hasFileEnd) {
			return "";
		}
		boolean hasFileAbbrt = checkFile(fileAbbreviations);
		if (!hasFileAbbrt) {
			return "";
		}
		Map<String, String> mapStart = convertFileToMap(fileStart);	
		Map<String, String> mapEnd = convertFileToMap(fileEnd);
		Map<String, String> mapAbbreviations = convertFileToMap(fileAbbreviations);

		Map<String, String> mapRacers = formMapRacers(mapStart, mapEnd, mapAbbreviations);
		return formOutputListRacers(mapRacers);
	}
	
	private String formOutputListRacers(Map<String, String> mapRacers) {
		String first15Racers = mapRacers.entrySet().stream().limit(15).map(x -> formOutputLine(x.getValue()))
				.collect(Collectors.joining());
		String separator = String.format("%s%n",
				"*------------------------------------------------------------------------");
		String lastRacers = mapRacers.entrySet().stream().skip(15).limit(15).map(x -> formOutputLine(x.getValue()))
				.collect(Collectors.joining());

		StringJoiner listRacer = new StringJoiner("");
		return listRacer.add(first15Racers).add(separator).add(lastRacers).toString();
	}

	private Map<String, String> formMapRacers(Map<String, String> mapStart, Map<String, String> mapEnd,
			Map<String, String> mapAbbreviations) {
		
		Map<String, String> mapTime = Stream.of(mapStart, mapEnd).flatMap(m -> m.entrySet().stream())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (o1,
						o2) -> Long.toString(reseiveTime(o1, o2).toMillis()) + "%" + reseiveTime(o1, o2).toString()));

		Map<String, String> fullSortedMap = Stream.of(mapTime, mapAbbreviations).flatMap(m -> m.entrySet().stream())
				.sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (o1, o2) -> o1 + o2, LinkedHashMap::new));
		
		return fullSortedMap;
	}

	private Duration reseiveTime(String timeStart, String timeEnd) {
		return Duration.between(convertStringToLocalDT(timeStart), convertStringToLocalDT(timeEnd));
	}

	private LocalDateTime convertStringToLocalDT(String text) {
		return LocalDateTime.parse(text.replace('_', ' '), DateTimeFormatter.ofPattern(PATTERN_DATA_TIME));
	}

	private Map<String, String> convertFileToMap(String fileAbbreviations) {
		Map<String, String> mapAbbreviations = new HashMap<>();
		try {
			mapAbbreviations = Files.lines(Paths.get(fileAbbreviations))
					.collect(Collectors.toMap(i -> i.substring(0, 3), i -> i.substring(3)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapAbbreviations;
	}
	
	private boolean checkFile(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("Null parameters are not allowed");
		}
		if (fileName.isEmpty()) {
			throw new IllegalArgumentException("Null parameters are not allowed");
		}
		return true;
	}
	
	private String formOutputLine(String nameRacer) {
		int serialNumber = 1;
		int indexName = nameRacer.indexOf(TEXT_SEPARATOR);
		int indexCar = nameRacer.indexOf(TEXT_SEPARATOR, indexName + 1);
		int indexTime = nameRacer.indexOf(TIME_SEPARATOR);
		String indentName = printCharacters(20 - (indexCar - indexName));
		String indentCar = printCharacters(30 - nameRacer.substring(indexCar + 1).length());
		String outputSerialNumber = outputSerialNumber(serialNumber);
		String outputLine = String.format("%s %s%s| %s%s| %s%n", outputSerialNumber,
				nameRacer.substring(indexName + 1, indexCar), indentName, nameRacer.substring(indexCar + 1), indentCar,
				nameRacer.substring(indexTime + 1, indexName).replace("PT", "").replace("M", ":").replace("S", ""));
		return outputLine;
	}

	private String outputSerialNumber(int serialNumber) {
		StringJoiner outputserialNumber = new StringJoiner("");
		String serialNumberString = String.valueOf(serialNumber);
		if (serialNumberString.length() == 1) {
			outputserialNumber.add(SINGLE_INDENT);
		}
		outputserialNumber.add(serialNumberString).add(".");
		return outputserialNumber.toString();
	}

	private String printCharacters(final int lenghtLine) {
		StringJoiner outputSymbol = new StringJoiner("");
		for (int i = 0; i < lenghtLine; i++) {
			outputSymbol.add(SINGLE_INDENT);
		}
		return outputSymbol.toString();
	}
}
