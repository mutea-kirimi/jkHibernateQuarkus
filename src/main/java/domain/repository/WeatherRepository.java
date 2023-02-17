package domain.repository;

import domain.common.paging.PagedResult;
import domain.model.town.Town;
import domain.model.weather.Weather;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface WeatherRepository {
    void save(Weather weather);
    void delete(UUID id);
    Optional<Weather> find(UUID id);
    Weather findOrFail(UUID id);
    PagedResult<Weather> allPaged(int pageIndex);
    List<Town> getTownsWithMaxWeatherEntries();
    List<String> getTownNamesWithMaxWeatherEntries();
    Map<String, Integer> getTownNoOfEntriesMap();
    List<UUID> getTownIdsWithMaxWeatherEntries();
    List<Weather> getCountryEntriesList(String country);
    List<Weather> all();
}
