package domain.service.weather.caching;

public interface CachableWeatherService {
    void clearCityNoOfEnriesCache();
    void initializeCityNoOfEnriesCache();
    void clearCountryEntriesCache(String country);
    void initializeCountryEntriesCache(String contry);
    void clearAllCountryEntriesCache();
}
