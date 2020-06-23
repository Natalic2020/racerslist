package ua.com.foxminded.racers;

import java.time.Duration;

public class RacerData {

	private static final String TEXT_SEPARATOR = "_";
	private String abbr = "";
	private String name = "";
	private String car = "";
	private Duration racerTime;
	
	public RacerData(String text) {
		abbr = text.substring(0,3);
		int indexSeparator = text.indexOf(TEXT_SEPARATOR, 5);
		name = text.substring(4,indexSeparator);
		car = text.substring(indexSeparator + 1);
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCar() {
		return car;
	}
	public void setCar(String car) {
		this.car = car;
	}
	public Duration getRacerTime() {
		return racerTime;
	}
	public void setRacerTime(Duration racerTime ) {
		this.racerTime = racerTime;
	}
	@Override
	public String toString() {
		return String.format("%0$-19s| %0$-30s| %s",  name, car, racerTime.toString().replace("PT", "").replace("M", ":").replace("S", ""));
	}		
}
