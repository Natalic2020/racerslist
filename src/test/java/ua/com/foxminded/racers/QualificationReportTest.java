package ua.com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class QualificationReportTest {

    FileReportParser reportParser;
    QualificationReport racersList;

    @Test
    public void buildRaceReport_shouldSortRacers_whenInputFiles() {

        reportParser = Mockito.mock(FileReportParser.class);
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();
        racerDataList.add(new RacerData("DMR", "Daniel", "MERCEDES"));
        racerDataList.add(new RacerData("LFR", "Lewis", "FERRARI"));
        racerDataList.add(new RacerData("SRN", "Sergey", "RENAULT"));
        Map<String, LocalDateTime> mapStart = new HashMap<>();
        mapStart.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 0, 0, 0));
        mapStart.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 0, 0, 0));
        mapStart.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 0, 0, 0));
        Map<String, LocalDateTime> mapEnd = new HashMap<>();
        mapEnd.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 4, 1, 100000000));
        mapEnd.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
        mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));

        Mockito.when(reportParser.parseRacersData(anyString())).thenReturn(racerDataList);
        Mockito.when(reportParser.parseTimeData(anyString())).thenReturn(mapStart).thenReturn(mapEnd);

        String expected = String
            .format("%s%n%s%n%s%n", " 1. Lewis              | FERRARI                       | 4:1.1", //
                    " 2. Sergey             | RENAULT                       | 10:0.258", //
                    " 3. Daniel             | MERCEDES                      | 15:2.252");

        String actual = racersList.buildRaceReport("testFileName", "testFileName", "testFileName");

        assertEquals(expected, actual);
    }

    @Test
    public void buildRaceReport_shouldReturnEmptyString_whenInputEmptyRacer() {

        reportParser = Mockito.mock(FileReportParser.class);
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();

        Map<String, LocalDateTime> mapStart = new HashMap<>();
        mapStart.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 0, 0, 0));
        mapStart.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 0, 0, 0));
        mapStart.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 0, 0, 0));
        Map<String, LocalDateTime> mapEnd = new HashMap<>();
        mapEnd.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 4, 1, 100000000));
        mapEnd.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
        mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));

        Mockito.when(reportParser.parseRacersData(anyString())).thenReturn(racerDataList);
        Mockito.when(reportParser.parseTimeData(anyString())).thenReturn(mapStart).thenReturn(mapEnd);

        String expected = "";

        String actual = racersList.buildRaceReport("testFileName", "testFileName", "testFileName");

        assertEquals(expected, actual);
    }     
    
    @Test
    public void buildRaceReport_shouldReturnEmptyString_whenInputEmptyStartTime() {

        reportParser = Mockito.mock(FileReportParser.class);
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();
        racerDataList.add(new RacerData("DMR", "Daniel", "MERCEDES"));
        racerDataList.add(new RacerData("LFR", "Lewis", "FERRARI"));
        racerDataList.add(new RacerData("SRN", "Sergey", "RENAULT"));
        Map<String, LocalDateTime> mapStart = new HashMap<>();
        
        Map<String, LocalDateTime> mapEnd = new HashMap<>();
        mapEnd.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 4, 1, 100000000));
        mapEnd.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
        mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));

        Mockito.when(reportParser.parseRacersData(anyString())).thenReturn(racerDataList);
        Mockito.when(reportParser.parseTimeData(anyString())).thenReturn(mapStart).thenReturn(mapEnd);

        String expected = "";

        String actual = racersList.buildRaceReport("testFileName", "testFileName", "testFileName");

        assertEquals(expected, actual);
    }
    
    @Test
    public void buildRaceReport_shouldReturnEmptyString_whenInputEmptyEndTime() {

        reportParser = Mockito.mock(FileReportParser.class);
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();
        racerDataList.add(new RacerData("DMR", "Daniel", "MERCEDES"));
        racerDataList.add(new RacerData("LFR", "Lewis", "FERRARI"));
        racerDataList.add(new RacerData("SRN", "Sergey", "RENAULT"));
        Map<String, LocalDateTime> mapStart = new HashMap<>();
        mapStart.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 0, 0, 0));
        mapStart.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 0, 0, 0));
        mapStart.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 0, 0, 0));
        Map<String, LocalDateTime> mapEnd = new HashMap<>();

        Mockito.when(reportParser.parseRacersData(anyString())).thenReturn(racerDataList);
        Mockito.when(reportParser.parseTimeData(anyString())).thenReturn(mapStart).thenReturn(mapEnd);

        String expected = "";

        String actual = racersList.buildRaceReport("testFileName", "testFileName", "testFileName");

        assertEquals(expected, actual);
    }
    
    @Test
    public void fillRacerListWithTime_shouldSortRacers_whenInputUnsortRacers() {
        reportParser = new FileReportParser();
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();
        racerDataList.add(new RacerData("DMR", "Daniel", "MERCEDES"));
        racerDataList.add(new RacerData("LFR", "Lewis", "FERRARI"));
        racerDataList.add(new RacerData("SRN", "Sergey", "RENAULT"));
        Map<String, LocalDateTime> mapStart = new HashMap<>();
        mapStart.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 0, 0, 0));
        mapStart.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 0, 0, 0));
        mapStart.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 0, 0, 0));
        Map<String, LocalDateTime> mapEnd = new HashMap<>();
        mapEnd.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 4, 1, 100000000));
        mapEnd.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
        mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));

        String expected = "[Lewis              | FERRARI                       | 4:1.1,"
                + " Sergey             | RENAULT                       | 10:0.258,"
                + " Daniel             | MERCEDES                      | 15:2.252]";

        List<RacerData> actual = racersList.fillRacerListWithTime(racerDataList, mapStart, mapEnd);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void fillRacerListWithTime_shouldExcludeInvalidRecord_whenInputStartTimeNull() {
        reportParser = new FileReportParser();
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();
        racerDataList.add(new RacerData("DMR", "Daniel", "MERCEDES"));
        racerDataList.add(new RacerData("LFR", "Lewis", "FERRARI"));
        racerDataList.add(new RacerData("SRN", "Sergey", "RENAULT"));
        Map<String, LocalDateTime> mapStart = new HashMap<>();
        mapStart.put("LFR", null);
        mapStart.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 0, 0, 0));
        mapStart.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 0, 0, 0));
        Map<String, LocalDateTime> mapEnd = new HashMap<>();
        mapEnd.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 4, 1, 100000000));
        mapEnd.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
        mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));

        String expected = "[Sergey             | RENAULT                       | 10:0.258, "
                + "Daniel             | MERCEDES                      | 15:2.252]";

        List<RacerData> actual = racersList.fillRacerListWithTime(racerDataList, mapStart, mapEnd);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void fillRacerListWithTime_shouldExcludeInvalidRecord_whenInputEndTimeNull() {
        reportParser = new FileReportParser();
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();
        racerDataList.add(new RacerData("DMR", "Daniel", "MERCEDES"));
        racerDataList.add(new RacerData("LFR", "Lewis", "FERRARI"));
        racerDataList.add(new RacerData("SRN", "Sergey", "RENAULT"));
        Map<String, LocalDateTime> mapStart = new HashMap<>();
        mapStart.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 0, 0, 0));
        mapStart.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 0, 0, 0));
        mapStart.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 0, 0, 0));
        Map<String, LocalDateTime> mapEnd = new HashMap<>();
        mapEnd.put("LFR", null);
        mapEnd.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
        mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));

        String expected = "[Sergey             | RENAULT                       | 10:0.258, "
                + "Daniel             | MERCEDES                      | 15:2.252]";

        List<RacerData> actual = racersList.fillRacerListWithTime(racerDataList, mapStart, mapEnd);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void fillRacerListwithTime_shouldExcludeInvalidRecord_whenInputNameNull() {
        reportParser = new FileReportParser();
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();
        racerDataList.add(new RacerData("DMR", null, "MERCEDES"));
        racerDataList.add(new RacerData("LFR", "Lewis", "FERRARI"));
        racerDataList.add(new RacerData("SRN", "Sergey", "RENAULT"));
        Map<String, LocalDateTime> mapStart = new HashMap<>();
        mapStart.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 0, 0, 0));
        mapStart.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 0, 0, 0));
        mapStart.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 0, 0, 0));
        Map<String, LocalDateTime> mapEnd = new HashMap<>();
        mapEnd.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 4, 1, 100000000));
        mapEnd.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
        mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));

        String expected = "[Lewis              | FERRARI                       | 4:1.1,"
                + " Sergey             | RENAULT                       | 10:0.258]";

        List<RacerData> actual = racersList.fillRacerListWithTime(racerDataList, mapStart, mapEnd);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void fillRacerListwithTime_shouldExcludeInvalidRecord_whenInputCarNull() {
        reportParser = new FileReportParser();
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();
        racerDataList.add(new RacerData("DMR", "Daniel", null));
        racerDataList.add(new RacerData("LFR", "Lewis", "FERRARI"));
        racerDataList.add(new RacerData("SRN", "Sergey", "RENAULT"));
        Map<String, LocalDateTime> mapStart = new HashMap<>();
        mapStart.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 0, 0, 0));
        mapStart.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 0, 0, 0));
        mapStart.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 0, 0, 0));
        Map<String, LocalDateTime> mapEnd = new HashMap<>();
        mapEnd.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 4, 1, 100000000));
        mapEnd.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
        mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));

        String expected = "[Lewis              | FERRARI                       | 4:1.1,"
                + " Sergey             | RENAULT                       | 10:0.258]";

        List<RacerData> actual = racersList.fillRacerListWithTime(racerDataList, mapStart, mapEnd);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void fillRacerListwithTime_shouldExcludeInvalidRecord_whenInputAbbrNull() {
        reportParser = new FileReportParser();
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();
        racerDataList.add(new RacerData(null, "Daniel", "MERCEDES"));
        racerDataList.add(new RacerData("LFR", "Lewis", "FERRARI"));
        racerDataList.add(new RacerData("SRN", "Sergey", "RENAULT"));
        Map<String, LocalDateTime> mapStart = new HashMap<>();
        mapStart.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 0, 0, 0));
        mapStart.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 0, 0, 0));
        mapStart.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 0, 0, 0));
        Map<String, LocalDateTime> mapEnd = new HashMap<>();
        mapEnd.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 4, 1, 100000000));
        mapEnd.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
        mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));

        String expected = "[Lewis              | FERRARI                       | 4:1.1, "
                + "Sergey             | RENAULT                       | 10:0.258]";

        List<RacerData> actual = racersList.fillRacerListWithTime(racerDataList, mapStart, mapEnd);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void fillRacerListwithTime_shouldReturnEmptyList_whenInputEmptyMapStart() {
        reportParser = new FileReportParser();
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();
        racerDataList.add(new RacerData("DMR", "Daniel", "MERCEDES"));
        racerDataList.add(new RacerData("LFR", "Lewis", "FERRARI"));
        racerDataList.add(new RacerData("SRN", "Sergey", "RENAULT"));
        Map<String, LocalDateTime> mapStart = new HashMap<>();
        Map<String, LocalDateTime> mapEnd = new HashMap<>();
        mapEnd.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 4, 1, 100000000));
        mapEnd.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
        mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));

        String expected = "[]";

        List<RacerData> actual = racersList.fillRacerListWithTime(racerDataList, mapStart, mapEnd);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void fillRacerListwithTime_shouldReturnEmptyList_whenInputEmptyMapEnd() {
        reportParser = new FileReportParser();
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();
        racerDataList.add(new RacerData("DMR", "Daniel", "MERCEDES"));
        racerDataList.add(new RacerData("LFR", "Lewis", "FERRARI"));
        racerDataList.add(new RacerData("SRN", "Sergey", "RENAULT"));
        Map<String, LocalDateTime> mapStart = new HashMap<>();
        mapStart.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 0, 0, 0));
        mapStart.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 0, 0, 0));
        mapStart.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 0, 0, 0));
        Map<String, LocalDateTime> mapEnd = new HashMap<>();

        String expected = "[]";

        List<RacerData> actual = racersList.fillRacerListWithTime(racerDataList, mapStart, mapEnd);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void fillRacerListwithTime_shouldExcludeOneRacerWithWrongAbbr_whenInputWrongAbbrByStart() {
        reportParser = new FileReportParser();
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();
        racerDataList.add(new RacerData("DMR", "Daniel", "MERCEDES"));
        racerDataList.add(new RacerData("LFR", "Lewis", "FERRARI"));
        racerDataList.add(new RacerData("SRN", "Sergey", "RENAULT"));
        Map<String, LocalDateTime> mapStart = new HashMap<>();
        mapStart.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 0, 0, 0));
        mapStart.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 0, 0, 0));
        mapStart.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 0, 0, 0));
        Map<String, LocalDateTime> mapEnd = new HashMap<>();
        mapEnd.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 4, 1, 100000000));
        mapEnd.put("DMK", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
        mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));

        String expected = String
            .format("%s%s", "[Lewis              | FERRARI                       | 4:1.1, ",
                    "Sergey             | RENAULT                       | 10:0.258]");

        List<RacerData> actual = racersList.fillRacerListWithTime(racerDataList, mapStart, mapEnd);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void fillRacerListwithTime_shouldReturnEmptyList_whenInputEmptyRacerDataList() {
        reportParser = new FileReportParser();
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<RacerData>();
        Map<String, LocalDateTime> mapStart = new HashMap<>();
        mapStart.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 0, 0, 0));
        mapStart.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 0, 0, 0));
        mapStart.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 0, 0, 0));
        Map<String, LocalDateTime> mapEnd = new HashMap<>();
        mapEnd.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 4, 1, 100000000));
        mapEnd.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
        mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));

        String expected = "[]";

        List<RacerData> actual = racersList.fillRacerListWithTime(racerDataList, mapStart, mapEnd);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void formOutputListRacers_shouldReturnTheSameRacers_whenInputLess15Racers() {
        reportParser = new FileReportParser();
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<>();
        RacerData racerData1 = new RacerData("DMR", "Daniel", "MERCEDES");
        RacerData racerData2 = new RacerData("LFR", "Lewis", "FERRARI");
        RacerData racerData3 = new RacerData("SRN", "Sergey", "RENAULT");
        racerData1.setBestTime(Duration.ofDays(1));
        racerData2.setBestTime(Duration.ofDays(1));
        racerData3.setBestTime(Duration.ofDays(1));
        racerDataList.add(racerData1);
        racerDataList.add(racerData2);
        racerDataList.add(racerData3);

        String expected = String
            .format("%s%n%s%n%s%n", " 1. Daniel             | MERCEDES                      | 24H",
                    " 2. Lewis              | FERRARI                       | 24H",
                    " 3. Sergey             | RENAULT                       | 24H");

        String actual = racersList.formOutputListRacers(racerDataList);

        assertEquals(expected, actual.toString());
    }

    @Test
    public void formOutputListRacers_shouldEndWithSeparator_whenInput15Racers() {
        reportParser = new FileReportParser();
        racersList = new QualificationReport(reportParser);

        List<RacerData> racerDataList = new ArrayList<>();
        RacerData racerData = new RacerData("DMR", "Daniel", "MERCEDES");
        racerData.setBestTime(Duration.ofDays(1));
        for (int i = 0; i < 15; i++) {
            racerDataList.add(racerData);
        }
        String actual = racersList.formOutputListRacers(racerDataList);
        assertTrue(actual
            .endsWith(String.format("%s%n", "*----------------------------------------------------------------")));
    }
}
