package domain.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Location {
    @Column(name = "town_name")
    private String name;
    private String country;
    private String region;
    private double lat;
    private double lon;
    private String timezone_id;
    private LocalDateTime localDateTime;
    private double utc_offset;

    public Location() {
    }

    public Location(String name, String country, String region, double lat, double lon, String timezone_id, LocalDateTime localDateTime, double utc_offset) {
        this.name = name;
        this.country = country;
        this.region = region;
        this.lat = lat;
        this.lon = lon;
        this.timezone_id = timezone_id;
        this.localDateTime = localDateTime;
        this.utc_offset = utc_offset;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getTimezone_id() {
        return timezone_id;
    }

    public double getUtc_offset() {
        return utc_offset;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", timezone_id='" + timezone_id + '\'' +
                ", localDateTime=" + localDateTime +
                ", utc_offset=" + utc_offset +
                '}';
    }
}
