package ua.com.foxminded.racers;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

		ParsingReader parsingReader = new ParsingReader();
		List<RacerData> racerDataList = parsingReader.parseFileToListRacerData(fileAbbreviations);

		Map<String, String> mapStart = parsingReader.parseFileToMap(fileStart);
		Map<String, String> mapEnd = parsingReader.parseFileToMap(fileEnd);

		Map<String, String> mapTime = Stream.of(mapStart, mapEnd)
				                      .flatMap(m -> 
				                               m.entrySet().stream())
				                      .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (o1, o2) -> o1 + o2));

		List<RacerData> racerDataListSorted = racerDataList.stream()
				.filter(s -> 
				        !mapTime.get(s.getAbbr()).isEmpty())
				.map(s -> {
					s.setRacerTime(parsingReader.parseStringToDuration(mapTime.get(s.getAbbr())));
					return s;
				})
				.sorted(Comparator.comparing(RacerData::getRacerTime))
				.collect(Collectors.toList());

		return racerDataListSorted;
	}

	private String reseivePath(String file) {
		return getClass().getClassLoader().getResource(file).getPath().substring(1);
	}

	private Stream<String> formOutputListRacers(List<RacerData> racerDataList) {
		Stream<String> first15 = formOutputListRacers(racerDataList, 1, 15);
		Stream<String> separator = Stream
				.of(String.format("%s%n", "*----------------------------------------------------------------"));
		Stream<String> last = formOutputListRacers(racerDataList, 16, racerDataList.size());
		return Stream.concat(Stream.concat(first15, separator), last);
	}

	private Stream<String> formOutputListRacers(List<RacerData> racerDataList, int start, int end) {
		return IntStream
				.range(start - 1, end)
				.mapToObj(i -> {
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
		return f.isFile();	
	}
}
