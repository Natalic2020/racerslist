package ua.com.foxminded.racers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RacerData {

	private static final String PATTERN_DATA_TIME = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final String TEXT_SEPARATOR = "_";
	private String abbr = "";
	private String name = "";
	private String car = "";
	private LocalDateTime startTime;
	private LocalDateTime endTime;
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
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public Duration getRacerTime() {
		return racerTime;
	}
	public void setRacerTime() {
		this.racerTime = Duration.between(this.startTime, this.endTime);
	}
	
	public void setTime (String timeString , boolean isStartTime  ) {
		if (isStartTime) {
			setStartTime(convertStringToLocalDT(timeString));
		}
		setEndTime(convertStringToLocalDT(timeString));
	}
	
	private LocalDateTime convertStringToLocalDT(String text) {
		return LocalDateTime.parse(text.replace('_', ' '), DateTimeFormatter.ofPattern(PATTERN_DATA_TIME));
	}

	@Override
	public String toString() {
		return String.format("%0$-19s| %0$-30s| %s",  name, car, racerTime.toString().replace("PT", "").replace("M", ":").replace("S", ""));
	}		
}
