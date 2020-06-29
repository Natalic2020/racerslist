package ua.com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Collectors;

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
				racersList.formRacersList(absolutePathStart, absolutePathEnd, absolutePathAbbr);
			});
	}

	@Test
	public void formRacersList_shouldThrowIllegalArgumentException_whenInputEmptyString() {
		final String absolutePathStart = "";
		String absolutePathEnd = "end.log";
		String absolutePathAbbr = "abbreviations.txt";
		assertThrows(IllegalArgumentException.class, () ->
			{
				racersList.formRacersList(absolutePathStart, absolutePathEnd, absolutePathAbbr);
			});
	}

	@Test
	public void testReadFileWithClassLoader() {

		String absolutePathStart = "start.log";
		String absolutePathEnd = "end.log";
		String absolutePathAbbr = "abbreviations.txt";
		String actual = "";

		actual = racersList.formRacersList(absolutePathStart, absolutePathEnd, absolutePathAbbr)
		                   .collect(Collectors.joining());

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
