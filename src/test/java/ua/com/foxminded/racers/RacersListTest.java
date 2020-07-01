package ua.com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
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
	public void formRacersList_shouldThrowIllegalArgumentException_whenInputNull() {
		final String absolutePathStart = null;
		String absolutePathEnd = "end.log";
		String absolutePathAbbr = "abbreviations.txt";
		assertThrows(IllegalArgumentException.class, () ->
			{
				racersList.qualificationReport(absolutePathStart, absolutePathEnd, absolutePathAbbr);
			});
	}

	@Test
	public void formRacersList_shouldThrowIllegalArgumentException_whenInputEmptyString() {
		final String absolutePathStart = "";
		String absolutePathEnd = "end.log";
		String absolutePathAbbr = "abbreviations.txt";
		assertThrows(IllegalArgumentException.class, () ->
			{
				racersList.qualificationReport(absolutePathStart, absolutePathEnd, absolutePathAbbr);
			});
	}
	
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
	public void fillRacerListwithTime_shouldSortRacersOneLess_whenInputStartNull() {
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
		String expected = String.format(
		        "%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n", //
		        " 1. Daniel             | MERCEDES                      | 24H", // 
				" 2. Daniel             | MERCEDES                      | 24H", // 
				" 3. Daniel             | MERCEDES                      | 24H", // 
				" 4. Daniel             | MERCEDES                      | 24H", // 
				" 5. Daniel             | MERCEDES                      | 24H", // 
				" 6. Daniel             | MERCEDES                      | 24H", // 
				" 7. Daniel             | MERCEDES                      | 24H", // 
				" 8. Daniel             | MERCEDES                      | 24H", // 
				" 9. Daniel             | MERCEDES                      | 24H", // 
				"10. Daniel             | MERCEDES                      | 24H", // 
				"11. Daniel             | MERCEDES                      | 24H", // 
				"12. Daniel             | MERCEDES                      | 24H", // 
				"13. Daniel             | MERCEDES                      | 24H", // 
				"14. Daniel             | MERCEDES                      | 24H", // 
				"15. Daniel             | MERCEDES                      | 24H", // 
				"*----------------------------------------------------------------"); // 			
		assertEquals(expected, actual.toString());
	}
	
	@Test
	public void qualificationReport_shouldSortedRacers_whenInputStartEndAbbrFiles() throws FileNotFoundException {

		String absolutePathStart = "start.log";
		String absolutePathEnd = "end.log";
		String absolutePathAbbr = "abbreviations.txt";
		String actual = "";

		actual = racersList.qualificationReport(absolutePathStart, absolutePathEnd, absolutePathAbbr);                  

		final String expected = String.format(
		        "%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n", //
		        " 1. Sebastian Vettel   | FERRARI                       | 1:4.415", //
		        " 2. Daniel Ricciardo   | RED BULL RACING TAG HEUER     | 1:12.013", //
		        " 3. Valtteri Bottas    | MERCEDES                      | 1:12.434", //
		        " 4. Lewis Hamilton     | MERCEDES                      | 1:12.46", //
		        " 5. Stoffel Vandoorne  | MCLAREN RENAULT               | 1:12.463", //
		        " 6. Kimi Raikkonen     | FERRARI                       | 1:12.639", //
		        " 7. Fernando Alonso    | MCLAREN RENAULT               | 1:12.657", //
		        " 8. Sergey Sirotkin    | WILLIAMS MERCEDES             | 1:12.706", //
		        " 9. Charles Leclerc    | SAUBER FERRARI                | 1:12.829", //
		        "10. Sergio Perez       | FORCE INDIA MERCEDES          | 1:12.848", //
		        "11. Romain Grosjean    | HAAS FERRARI                  | 1:12.93", //
		        "12. Pierre Gasly       | SCUDERIA TORO ROSSO HONDA     | 1:12.941", //
		        "13. Carlos Sainz       | RENAULT                       | 1:12.95", //
		        "14. Esteban Ocon       | FORCE INDIA MERCEDES          | 1:13.028", //
		        "15. Nico Hulkenberg    | RENAULT                       | 1:13.065", //
		        "*----------------------------------------------------------------", //
		        "16. Brendon Hartley    | SCUDERIA TORO ROSSO HONDA     | 1:13.179", //
		        "17. Marcus Ericsson    | SAUBER FERRARI                | 1:13.265", //
		        "18. Lance Stroll       | WILLIAMS MERCEDES             | 1:13.323", //
		        "19. Kevin Magnussen    | HAAS FERRARI                  | 1:13.393", //
		        ""); //
		assertEquals(expected, actual);
	}
}
