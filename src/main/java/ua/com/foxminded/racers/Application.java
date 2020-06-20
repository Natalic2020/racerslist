package ua.com.foxminded.racers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;

public class Application {

	public static void main(String[] args) {
		String fileStart = "c://Курсы//start.log";
		String fileEnd = "c://Курсы//end.log";
		String fileAbbreviations = "c://Курсы//abbreviations.txt";
			
		RacersList racersList = new RacersList();
		try {
			System.out.println(racersList.formRacersList(fileStart, fileEnd, fileAbbreviations));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
