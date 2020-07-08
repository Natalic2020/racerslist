package ua.com.foxminded.racers;

public class Application {

	public static void main(String[] args)  {

		String fileStart = "start.log";
		String fileEnd = "end.log";
		String fileAbbr = "abbreviations.txt";

		QualificationReport raceReport = new QualificationReport();

		try {
			System.out.print(raceReport.buildRaceReport(fileStart, fileEnd, fileAbbr));

		} catch (IllegalArgumentException e) {
			System.out.println("Invalid  input:\"" + e.getMessage() + "\"");
		}
	}
}
