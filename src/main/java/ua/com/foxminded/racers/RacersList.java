package ua.com.foxminded.racers;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RacersList {

	public Stream<String> formRacersList(String fileStart, String fileEnd, String fileAbbreviations) {
		ParsingReader parsingReader = new ParsingReader();

		List<RacerData> racerDataList = parsingReader.parseFileToListRacerData(fileAbbreviations);
		Map<String, LocalDateTime> mapStart = parsingReader.parseFileToMap(fileStart);
		Map<String, LocalDateTime> mapEnd = parsingReader.parseFileToMap(fileEnd);

		List<RacerData> racerData = fillRacerListwithTime(racerDataList, mapStart, mapEnd);
		return formOutputListRacers(racerData);
	}

	private List<RacerData> fillRacerListwithTime(List<RacerData> racerDataList, Map<String, LocalDateTime> mapStart,
	        Map<String, LocalDateTime> mapEnd) {

		ParsingReader parsingReader = new ParsingReader();
		List<RacerData> racerDataListSorted = racerDataList.stream()
		                                                   .map(s ->
			                                                   {
				                                                   s.setRacerTime(parsingReader.parseStringToDuration(
				                                                           mapStart.get(s.getAbbr()),
				                                                           mapEnd.get(s.getAbbr())));
				                                                   return s;
			                                                   })
		                                                   .sorted(Comparator.comparing(RacerData::getRacerTime))
		                                                   .collect(Collectors.toList());

		return racerDataListSorted;
	}

	private Stream<String> formOutputListRacers(List<RacerData> racerDataList) {
		Stream<String> first15 = formOutputListRacers(racerDataList, 1, 15);
		Stream<String> separator = Stream
		                                 .of(String.format("%s%n",
		                                         "*----------------------------------------------------------------"));
		Stream<String> last = formOutputListRacers(racerDataList, 16, racerDataList.size());
		return Stream.concat(Stream.concat(first15, separator), last);
	}

	private Stream<String> formOutputListRacers(List<RacerData> racerDataList, int start, int end) {
		return IntStream.range(start - 1, end)
		                .mapToObj(i ->
			                {
				                RacerData racer = racerDataList.get(i);
				                return String.format("%2d. %s%n", i + 1, racer.toString());
			                });
	}
}
