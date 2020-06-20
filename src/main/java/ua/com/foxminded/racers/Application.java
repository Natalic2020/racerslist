package ua.com.foxminded.racers;

import java.io.File;

public class Application {

	public static void main(String[] args) {
		String path = "src/main/resources";	        
	    File file = new File(path);
	    String absolutePathStart = file.getAbsolutePath() + "\\start.log";
        String absolutePathEnd = file.getAbsolutePath() + "\\end.log";
        String absolutePathAbbr = file.getAbsolutePath() + "\\abbreviations.txt";

		RacersList racersList = new RacersList();
	
		try {
			System.out.println(racersList.formRacersList(absolutePathStart, absolutePathEnd, absolutePathAbbr));
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid  input:\"" + e.getMessage() + "\"");
		}		
	}
}
