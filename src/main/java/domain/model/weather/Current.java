package domain.model.weather;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Embeddable
public class Current {

    private int temperature;
    private int weather_code;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "current_weather_icons",
            joinColumns = @JoinColumn(name = "weather_id"))
    @Column(name = "icon")
    @Fetch(FetchMode.SELECT)
    private Set<String> weather_icons;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "current_weather_descriptions",
            joinColumns = @JoinColumn(name = "weather_id"))
    @Column(name = "description")
    @Fetch(FetchMode.SELECT)
    private Set<String> weather_descriptions;
    private int wind_speed;
    private int wind_degree;
    private String wind_dir;
    private int pressure;
    private int precip;
    private int humidity;
    private int cloudcover;
    private int feelslike;
    private int uv_index;

    public Current() {
    }

    public Current(int temperature, int weather_code, Set<String> weather_icons, Set<String> weather_descriptions, int wind_speed, int wind_degree, String wind_dir, int pressure, int precip, int humidity, int cloudcover, int feelslike, int uv_index) {
        this.temperature = temperature;
        this.weather_code = weather_code;
        this.weather_icons = weather_icons;
        this.weather_descriptions = weather_descriptions;
        this.wind_speed = wind_speed;
        this.wind_degree = wind_degree;
        this.wind_dir = wind_dir;
        this.pressure = pressure;
        this.precip = precip;
        this.humidity = humidity;
        this.cloudcover = cloudcover;
        this.feelslike = feelslike;
        this.uv_index = uv_index;
    }

    public int getTemprature() {
        return temperature;
    }

    public int getWeather_code() {
        return weather_code;
    }

    public Set<String> getWeather_icons() {
        return weather_icons;
    }

    public Set<String> getWeather_descriptions() {
        return weather_descriptions;
    }

    public int getWind_speed() {
        return wind_speed;
    }

    public int getWind_degree() {
        return wind_degree;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public int getPressure() {
        return pressure;
    }

    public int getPrecip() {
        return precip;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCloudcover() {
        return cloudcover;
    }

    public int getFeelslike() {
        return feelslike;
    }

    public int getUv_index() {
        return uv_index;
    }
}
