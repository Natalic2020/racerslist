package ua.com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class RacersListTest {

	RacersList racersList = new RacersList();
	
	@Test
	public void fillRacerListwithTime_shouldSortRacers_whenInputUnsortRacers() {
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
		
		List<RacerData> actual = racersList.fillRacerListwithTime(racerDataList, mapStart, mapEnd); 
		String expected = "[Lewis              | FERRARI                       | 4:1.1, Sergey             | RENAULT                       | 10:0.258, Daniel             | MERCEDES                      | 15:2.252]";
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void fillRacerListwithTime_shouldExcludeInvalidRecord_whenInputStartTimeNull() {
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
		
		List<RacerData> actual = racersList.fillRacerListwithTime(racerDataList, mapStart, mapEnd); 
		String expected = "[Sergey             | RENAULT                       | 10:0.258, Daniel             | MERCEDES                      | 15:2.252]";
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void fillRacerListwithTime_shouldSortRacersOneNameEmpty_whenInputNameNull() {
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
		
		List<RacerData> actual = racersList.fillRacerListwithTime(racerDataList, mapStart, mapEnd); 
		String expected = "[Lewis              | FERRARI                       | 4:1.1, Sergey             | RENAULT                       | 10:0.258,                    | MERCEDES                      | 15:2.252]";
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void fillRacerListwithTime_shouldSortRacersOneLess_whenInputAbbrNull() {
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
		
		List<RacerData> actual = racersList.fillRacerListwithTime(racerDataList, mapStart, mapEnd); 
		String expected = "[Lewis              | FERRARI                       | 4:1.1, Sergey             | RENAULT                       | 10:0.258]";
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void fillRacerListwithTime_shouldEmptyList_whenInputEmptyMapStart() {
		List<RacerData> racerDataList = new ArrayList<RacerData>();
		racerDataList.add(new RacerData("DMR", "Daniel", "MERCEDES"));
		racerDataList.add(new RacerData("LFR", "Lewis", "FERRARI"));
		racerDataList.add(new RacerData("SRN", "Sergey", "RENAULT"));
		Map<String, LocalDateTime> mapStart = new HashMap<>();
		Map<String, LocalDateTime> mapEnd = new HashMap<>();
		mapEnd.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 4, 1, 100000000));
		mapEnd.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
		mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));
		
		List<RacerData> actual = racersList.fillRacerListwithTime(racerDataList, mapStart, mapEnd); 
		String expected = "[]";
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void fillRacerListwithTime_shouldSortRacersOneLess_whenInputWrongAbbrByStart() {
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
		
		List<RacerData> actual = racersList.fillRacerListwithTime(racerDataList, mapStart, mapEnd); 
		String expected = "[Lewis              | FERRARI                       | 4:1.1, Sergey             | RENAULT                       | 10:0.258]";
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void fillRacerListwithTime_shouldEmptyList_whenInputEmptyRacerDataList() {
		List<RacerData> racerDataList = new ArrayList<RacerData>();
		Map<String, LocalDateTime> mapStart = new HashMap<>();
		mapStart.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 0, 0, 0));
		mapStart.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 0, 0, 0));
		mapStart.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 0, 0, 0));
		Map<String, LocalDateTime> mapEnd = new HashMap<>();
		mapEnd.put("LFR", LocalDateTime.of(2018, 05, 24, 12, 4, 1, 100000000));
		mapEnd.put("DMR", LocalDateTime.of(2018, 05, 24, 13, 15, 2, 252000000));
		mapEnd.put("SRN", LocalDateTime.of(2018, 05, 24, 14, 10, 0, 258000000));
		
		List<RacerData> actual = racersList.fillRacerListwithTime(racerDataList, mapStart, mapEnd); 
		String expected = "[]";
		assertEquals(expected, actual.toString());
	}

	@Test
	public void fillRacerListwithTime_shouldAtTheEndSelector_whenInput15Racers() {
		List<RacerData> racerDataList = new ArrayList<>();
		RacerData racerData = new RacerData("DMR", "Daniel", "MERCEDES");
		racerData.setRacerTime(Duration.ofDays(1));
		for (int i = 0; i < 15; i++) {
			racerDataList.add(racerData);
		}
		String actual = racersList.formOutputListRacers(racerDataList); 	
		assertTrue(actual.endsWith(String.format("%s%n", "*----------------------------------------------------------------")));
	}
}
