package ua.com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class QualificationReportMockitoRunWithTest {

    FileReportParser reportParser;
    QualificationReport racersList;
  
    @BeforeEach
     void init() {
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
        
        Mockito.when(reportParser.parseRacersData("abbr.file")).thenReturn(racerDataList);
        Mockito.when(reportParser.parseTimeData(Mockito.anyString())).thenReturn(mapStart).thenReturn(mapEnd);         
    }
    
    @Test
    public void buildRaceReport_shouldSortRacers_whenInputFiles() {   
        
        String expected =String.format("%s%n%s%n%s%n",
                " 1. Lewis              | FERRARI                       | 4:1.1", // 
                " 2. Sergey             | RENAULT                       | 10:0.258", //  
                " 3. Daniel             | MERCEDES                      | 15:2.252" );
        
        String actual = racersList.buildRaceReport( toString(), toString(), "abbr.file");

        assertEquals(expected, actual);
    }
}
