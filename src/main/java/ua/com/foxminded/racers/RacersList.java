package ua.com.foxminded.racers;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RacersList {

	public String qualificationReport(String fileStart, String fileEnd, String fileAbbreviations) throws FileNotFoundException {
		FileReportParser parsingReader = new FileReportParser();

		List<RacerData> racerDataList = parsingReader.parseFileToListRacerData(fileAbbreviations);
		Map<String, LocalDateTime> mapStart = parsingReader.parseFileToMap(fileStart);
		Map<String, LocalDateTime> mapEnd = parsingReader.parseFileToMap(fileEnd);

		List<RacerData> racerData = fillRacerListwithTime(racerDataList, mapStart, mapEnd);
		return formOutputListRacers(racerData);
	}

	protected List<RacerData> fillRacerListwithTime(List<RacerData> racerDataList, Map<String, LocalDateTime> mapStart,
	        Map<String, LocalDateTime> mapEnd) {

		FileReportParser parsingReader = new FileReportParser();
		List<RacerData> racerDataListSorted = racerDataList.stream()
		        .map(s ->
			        {
				        s.setRacerTime(parsingReader.parseStringToDuration(mapStart.get(s.getAbbr()),
				                mapEnd.get(s.getAbbr())));
				        return s;
			        })
		        .filter(s -> s.getRacerTime() != Duration.ZERO)
		        .sorted(Comparator.comparing(RacerData::getRacerTime))
		        .collect(Collectors.toList());

		return racerDataListSorted;
	}

	protected String formOutputListRacers(List<RacerData> racerDataList) {
		String first15 = formOutputListRacers(racerDataList, 1, 15);
		String separator = String.format("%s%n", "*----------------------------------------------------------------");
		String last = formOutputListRacers(racerDataList, 16, racerDataList.size());

		return String.format("%s%s%s", first15, separator, last);
	}

	private String formOutputListRacers(List<RacerData> racerDataList, int start, int end) {
		return IntStream.range(start - 1, end)
		        .mapToObj(i ->
			        {
				        RacerData racer = racerDataList.get(i);
				        return String.format("%2d. %s%n", i + 1, racer.toString());
			        })
		        .collect(Collectors.joining());
	}
}
