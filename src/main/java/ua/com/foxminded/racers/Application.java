package ua.com.foxminded.racers;

public class Application {

	public static void main(String[] args) {

	    String fileStart = "start.log";
        String fileEnd =  "end.log";
        String fileAbbr =  "abbreviations.txt";

		RacersList racersList = new RacersList();
	
		try {
			racersList.formRacersList(fileStart, fileEnd, fileAbbr)
			          .forEach(x->
			          {System.out.print(x);
			          });
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid  input:\"" + e.getMessage() + "\"");
		}		
	}
}
