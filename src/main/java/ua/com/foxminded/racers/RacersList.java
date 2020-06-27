package ua.com.foxminded.racers;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RacersList {

	public Stream<String> formRacersList(String fileStart, String fileEnd, String fileAbbreviations) {
		List<RacerData> racerDataList = formRacerList(fileStart, fileEnd, fileAbbreviations);
		return formOutputListRacers(racerDataList);
	}

	private List<RacerData> formRacerList(String fileStartName, String fileEndName, String fileAbbreviationsName) {

		ParsingReader parsingReader = new ParsingReader();
		String fileStart = parsingReader.reseivePath(fileStartName);
		String fileEnd = parsingReader.reseivePath(fileEndName);
		String fileAbbreviations = parsingReader.reseivePath(fileAbbreviationsName);

		List<RacerData> racerDataList = parsingReader.parseFileToListRacerData(fileAbbreviations);

		Map<String, String> mapStart = parsingReader.parseFileToMap(fileStart);
		Map<String, String> mapEnd = parsingReader.parseFileToMap(fileEnd);

		List<RacerData> racerDataListSorted = racerDataList.stream()
		.map(s -> {
			s.setRacerTime(parsingReader.parseStringToDuration(Stream.of(mapStart)
					                                                     .filter(s1 -> 
					                                                             !s1.get(s.getAbbr()).isEmpty())
					                                                     .map(s1 ->
					                                                            s1.get(s.getAbbr()))
					                                                     .collect(Collectors.joining())
					                                           , Stream.of(mapEnd)
					                                                     .filter(s2 -> 
					                                                            !s2.get(s.getAbbr()).isEmpty())
					                                                     .map(s2 -> 
					                                                     s2.get(s.getAbbr()))
					                                                     .collect(Collectors.joining())
					                                           ));
			return s;
		})
		.sorted(Comparator.comparing(RacerData::getRacerTime))
		.collect(Collectors.toList());
		
		return racerDataListSorted;
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
}
