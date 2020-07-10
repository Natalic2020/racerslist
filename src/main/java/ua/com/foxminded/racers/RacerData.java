package ua.com.foxminded.racers;

import java.time.Duration;
import java.util.Optional;

public class RacerData {

    private String abbr;
    private String name;
    private String car;
    private Duration bestTime;

    public RacerData(String abbr, String name, String car) {
        this.abbr = Optional.ofNullable(abbr).orElse("");
        this.name = Optional.ofNullable(name).orElse("");
        this.car = Optional.ofNullable(car).orElse("");
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
        return String
            .format("%0$-19s| %0$-30s| %s", name, car,
                    bestTime.toString().replace("PT", "").replace("M", ":").replace("S", ""));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((abbr == null) ? 0 : abbr.hashCode());
        result = prime * result + ((bestTime == null) ? 0 : bestTime.hashCode());
        result = prime * result + ((car == null) ? 0 : car.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RacerData other = (RacerData) obj;
        if (abbr == null) {
            if (other.abbr != null)
                return false;
        } else if (!abbr.equals(other.abbr))
            return false;
        if (bestTime == null) {
            if (other.bestTime != null)
                return false;
        } else if (!bestTime.equals(other.bestTime))
            return false;
        if (car == null) {
            if (other.car != null)
                return false;
        } else if (!car.equals(other.car))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
