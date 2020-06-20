package ua.com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class RacersListTest {

	RacersList racersList = new RacersList();
	String path = "src/test/resources";	        
    File file = new File(path);
	
	@Test
    void formRacersList_shouldThrowIllegalArgumentException_whenInputNull() {
		final String absolutePathStart = null;
        final String absolutePathEnd = file.getAbsolutePath() + "\\end.log";
        final String absolutePathAbbr = file.getAbsolutePath() + "\\abbreviations.txt";
        assertThrows(IllegalArgumentException.class, () -> {
        	racersList.formRacersList(absolutePathStart, absolutePathEnd, absolutePathAbbr);;
        });
    }

    @Test
    void formRacersList_shouldThrowIllegalArgumentException_whenInputEmptyString() {
    	final String absolutePathStart = "";
        final String absolutePathEnd = file.getAbsolutePath() + "\\end.log";
        final String absolutePathAbbr = file.getAbsolutePath() + "\\abbreviations.txt";
        assertThrows(IllegalArgumentException.class, () -> {
        	racersList.formRacersList(absolutePathStart, absolutePathEnd, absolutePathAbbr);;
        });
    }
	
	 @Test
	    public void testReadFileWithClassLoader(){
	        ClassLoader classLoader = this.getClass().getClassLoader();
	        
	       
	        String absolutePathStart = file.getAbsolutePath() + "\\start.log";
	        String absolutePathEnd = file.getAbsolutePath() + "\\end.log";
	        String absolutePathAbbr = file.getAbsolutePath() + "\\abbreviations.txt";
	        String actual = "";
	        
		    actual = racersList.formRacersList(absolutePathStart, absolutePathEnd, absolutePathAbbr);
			
	        final String expected = String.format("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n", //
	        		" 1. Sebastian Vettel   | FERRARI                       | 1:4.415",  // 
	        		" 1. Daniel Ricciardo   | RED BULL RACING TAG HEUER     | 1:12.013",  // 
	        		" 1. Valtteri Bottas    | MERCEDES                      | 1:12.434",  // 
	        		" 1. Lewis Hamilton     | MERCEDES                      | 1:12.46",  // 
	        		" 1. Stoffel Vandoorne  | MCLAREN RENAULT               | 1:12.463",  // 
	        		" 1. Kimi Raikkonen     | FERRARI                       | 1:12.639",  // 
	        		" 1. Fernando Alonso    | MCLAREN RENAULT               | 1:12.657",  // 
	        		" 1. Sergey Sirotkin    | WILLIAMS MERCEDES             | 1:12.706",  // 
	        		" 1. Charles Leclerc    | SAUBER FERRARI                | 1:12.829",  // 
	        		" 1. Sergio Perez       | FORCE INDIA MERCEDES          | 1:12.848",  // 
	        		" 1. Romain Grosjean    | HAAS FERRARI                  | 1:12.93",  // 
	        		" 1. Pierre Gasly       | SCUDERIA TORO ROSSO HONDA     | 1:12.941",  // 
	        		" 1. Carlos Sainz       | RENAULT                       | 1:12.95",  // 
	        		" 1. Esteban Ocon       | FORCE INDIA MERCEDES          | 1:13.028",  // 
	        		" 1. Nico Hulkenberg    | RENAULT                       | 1:13.065",  // 
	        		"*------------------------------------------------------------------------",  // 
	        		" 1. Brendon Hartley    | SCUDERIA TORO ROSSO HONDA     | 1:13.179",  // 
	        		" 1. Marcus Ericsson    | SAUBER FERRARI                | 1:13.265",  // 
	        		" 1. Lance Stroll       | WILLIAMS MERCEDES             | 1:13.323",  // 
	        		" 1. Kevin Magnussen    | HAAS FERRARI                  | 1:13.393",  // 
	        		""); //
	      
	        assertEquals(expected, actual);
	    }

}
