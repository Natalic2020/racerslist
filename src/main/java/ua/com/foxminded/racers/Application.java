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
			racersList.formRacersList(absolutePathStart, absolutePathEnd, absolutePathAbbr).forEach(x->{System.out.println(x);});
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid  input:\"" + e.getMessage() + "\"");
		}		
	}
}
