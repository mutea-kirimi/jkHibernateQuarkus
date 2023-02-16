package domain.service.weather;

import domain.model.weather.Weather;

public interface WeatherImportService<T> {
    T getCurrentWeatherForCity(String city);
}
