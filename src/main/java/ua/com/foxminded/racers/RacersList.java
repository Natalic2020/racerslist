package ua.com.foxminded.racers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
		boolean isStartTime = true;
		joinTimeToRacerList(fileStart, racerDataList, isStartTime);
		joinTimeToRacerList(fileEnd, racerDataList, !isStartTime);
		
		racerDataList.stream().forEach(RacerData::setRacerTime);
		racerDataList.sort(Comparator.comparing(RacerData::getRacerTime));
		
		return racerDataList;
	}

	private void joinTimeToRacerList(String fileStart, List<RacerData> racerDataList, boolean isStartTime) {
		try  ( Stream<String> fileInStream = Files.lines(Paths.get(fileStart))) {
			fileInStream.forEachOrdered(s -> {
				addTime ( s, racerDataList, isStartTime);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addTime (String s, List<RacerData> racerDataList, boolean isStartTime) {
		for (RacerData racerData : racerDataList) {
			if (racerData.getAbbr().equals(s.substring(0, 3))) {
					racerData.setTime(s.substring(3), isStartTime);
			}
		}
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
