package ua.com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QualificationReportMockitoTest {
    
    @Mock 
    FileReportParser reportParserMock;

    @InjectMocks 
    QualificationReport racersList = new QualificationReport(reportParserMock);
  
    @Test
    public void fillRacerListWithTime_shouldSortRacers_whenInputUnsortRacers() {
           
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

        List<RacerData> expected = new ArrayList<RacerData>();
        expected.add(new RacerData("DMR", "Daniel", "MERCEDES", Duration.parse("PT4M1.1S")));
        expected.add(new RacerData("LFR", "Lewis", "FERRARI", Duration.parse("PT10M0.258S")));
        expected.add(new RacerData("SRN", "Sergey", "RENAULT", Duration.parse("PT15M2.252S")));
          
        Mockito.when(racersList.fillRacerListWithTime(racerDataList, mapStart, mapEnd)).thenReturn(expected);
        Mockito.verify(racersList).fillRacerListWithTime(racerDataList, mapStart, mapEnd);

    }
}

