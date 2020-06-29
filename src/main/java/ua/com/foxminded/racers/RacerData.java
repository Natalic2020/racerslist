package ua.com.foxminded.racers;

import java.time.Duration;

public class RacerData {

	private String abbr = "";
	private String name = "";
	private String car = "";
	private Duration racerTime;

	public RacerData(String abbr, String name, String car) {
		this.abbr = abbr;
		this.name = name;
		this.car = car;
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

	public void setRacerTime(Duration racerTime) {
		this.racerTime = racerTime;
	}

	@Override
	public String toString() {
		return String.format("%0$-19s| %0$-30s| %s", name, car, racerTime.toString()
		                                                                 .replace("PT", "")
		                                                                 .replace("M", ":")
		                                                                 .replace("S", ""));
	}
}
