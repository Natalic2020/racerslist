package ua.com.foxminded.racers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QualificationReport {

    public static final int AMOUNT_RACERS_FOR_Q2 = 15;

    FileReportParser reportParser;
    
    public QualificationReport(FileReportParser reportParser) {
        this.reportParser = reportParser;
    }
    
    public String buildRaceReport(String fileStart, String fileEnd, String fileAbbreviations) {
        
        List<RacerData> racerDataList = reportParser.parseRacersData(fileAbbreviations);
        Map<String, LocalDateTime> mapStart = reportParser.parseTimeData(fileStart);
        Map<String, LocalDateTime> mapEnd = reportParser.parseTimeData(fileEnd);

        List<RacerData> racerData = fillRacerListWithTime(racerDataList, mapStart, mapEnd);
        return formOutputListRacers(racerData);
    }

    protected List<RacerData> fillRacerListWithTime(List<RacerData> racerDataList, Map<String, LocalDateTime> mapStart,
            Map<String, LocalDateTime> mapEnd) {

        List<RacerData> racerDataListSorted = racerDataList.stream().map(s ->
            {
                if (mapStart.get(s.getAbbr()) != null && mapEnd.get(s.getAbbr()) != null) { 
                s.setBestTime(Duration.between(mapStart.get(s.getAbbr()), mapEnd.get(s.getAbbr())));
               }
                else {
                    s.setBestTime(Duration.ZERO);  
                }
                return s;
                })
            .filter(racer -> !racer.getAbbr().isEmpty())
            .filter(racer -> !racer.getName().isEmpty())
            .filter(racer -> !racer.getCar().isEmpty())
            .filter(racer -> !racer.getBestTime().isZero())
            .sorted(Comparator.comparing(RacerData::getBestTime))
            .collect(Collectors.toList());

        return racerDataListSorted;
    }

    protected String formOutputListRacers(List<RacerData> racerDataList) {
        if (racerDataList.size() < AMOUNT_RACERS_FOR_Q2) {
            return String.format("%s", formOutputListRacers(racerDataList, 1, racerDataList.size()));
        }
        String racersForQ2 = formOutputListRacers(racerDataList, 1, AMOUNT_RACERS_FOR_Q2);
        String separator = String.format("%s%n", "*----------------------------------------------------------------");
        String lastRacers = formOutputListRacers(racerDataList, AMOUNT_RACERS_FOR_Q2 + 1, racerDataList.size());

        return String.format("%s%s%s", racersForQ2, separator, lastRacers);
    }

    private String formOutputListRacers(List<RacerData> racerDataList, int start, int end) {
        return IntStream.range(start - 1, end).mapToObj(i ->
            {
                RacerData racer = racerDataList.get(i);
                return String.format("%2d. %s%n", i + 1, racer.toString());
            }).collect(Collectors.joining());
    }
}
