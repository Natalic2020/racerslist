package ua.com.foxminded.racers;

import java.io.FileNotFoundException;

public class Application {

	public static void main(String[] args)  {

		String fileStart = "start.log";
		String fileEnd = "end.log";
		String fileAbbr = "abbreviations.txt";

		RacersList racersList = new RacersList();

		try {
			System.out.print(racersList.qualificationReport(fileStart, fileEnd, fileAbbr));

		} catch (IllegalArgumentException e) {
			System.out.println("Invalid  input:\"" + e.getMessage() + "\"");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
