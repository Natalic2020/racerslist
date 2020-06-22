package ua.com.foxminded.racers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RacersList {

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
		racerDataList.sort(Comparator.comparing(RacerData::getRacerTime));
		return formOutputListRacers(racerDataList);
	}

	private List<RacerData> formRacerList(String fileStart, String fileEnd, String fileAbbreviations) {
		Stream<String> abbrStream = receiveStream(fileAbbreviations);
		List<RacerData> racerDataList = abbrStream.map(temp -> new RacerData(temp)).collect(Collectors.toList());
		
		Stream<String> startStream = receiveStream(fileStart);
		startStream.forEachOrdered(s -> {
			for (RacerData racerData : racerDataList) {
				if (racerData.getAbbr().equals(s.substring(0, 3))) {
					racerData.setStartTime(s.substring(3));
				}
			}
		});
		Stream<String> endStream = receiveStream(fileEnd);
		endStream.forEachOrdered(s -> {
			for (RacerData racerData : racerDataList) {
				if (racerData.getAbbr().equals(s.substring(0, 3))) {
					racerData.setEndTime(s.substring(3));
					racerData.setRacerTime();
				}
			}
		});
		return racerDataList;
	}

	private Stream<String> formOutputListRacers(List<RacerData> racerDataList) {
		Stream<String> first15 = formOutputListRacers(racerDataList, 1, 15);
		Stream<String> separator = Stream.of(String.format("%s%n","*----------------------------------------------------------------"));
		Stream<String> last = formOutputListRacers(racerDataList, 16, racerDataList.size());
		return Stream.concat(Stream.concat(first15, separator), last);
	}

	private Stream<String> receiveStream(String file) {
		Stream<String> fileInStream = Stream.empty();
		try {
			fileInStream = Files.lines(Paths.get(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileInStream;
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
		return true;
	}
}
