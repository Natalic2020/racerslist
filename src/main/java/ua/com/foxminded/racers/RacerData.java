package ua.com.foxminded.racers;

import java.time.Duration;
import java.util.Optional;

public class RacerData {

	private String abbr;
	private String name;
	private String car;
	private Duration bestTime;

	public RacerData(String abbr, String name, String car) {
		this.abbr = Optional.ofNullable(abbr)
		        .orElse("");
		this.name = Optional.ofNullable(name)
		        .orElse("");
		this.car = Optional.ofNullable(car)
		        .orElse("");
		this.bestTime = Duration.ZERO;
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

	public Duration getBestTime() {
		return bestTime;
	}

	public void setBestTime(Duration bestTime) {
		this.bestTime = bestTime;
	}

	@Override
	public String toString() {
		return String.format("%0$-19s| %0$-30s| %s", name, car, bestTime.toString()
		        .replace("PT", "")
		        .replace("M", ":")
		        .replace("S", ""));
	}
}
