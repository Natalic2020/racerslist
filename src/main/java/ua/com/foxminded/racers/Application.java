package ua.com.foxminded.racers;

import java.io.IOException;

public class Application {

	public static void main(String[] args) {
		String fileStart = "c://Курсы//start.log";
		String fileEnd = "c://Курсы//end.log";
		String fileAbbreviations = "c://Курсы//abbreviations.txt";
			
		RacersList racersList = new RacersList();
		
			System.out.println(racersList.formRacersList(fileStart, fileEnd, fileAbbreviations));
		
	}

}
