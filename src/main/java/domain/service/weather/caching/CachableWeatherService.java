package domain.service.weather.caching;

import domain.common.paging.PagedResult;
import domain.model.weather.Weather;

import java.util.List;

public interface CachableWeatherService {
    void clearCityNoOfEnriesCache();
    void initializeCityNoOfEnriesCache();
    void clearCountryEntriesCache(String country);
    void initializeCountryEntriesCache(String contry);
    void clearAllCountryEntriesCache();
    PagedResult<Weather> allPaged(int idx);
    List<Weather> all();
}
