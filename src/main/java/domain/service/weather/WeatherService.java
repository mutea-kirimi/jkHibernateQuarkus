package domain.service.weather;

import domain.common.paging.PagedResult;
import domain.model.town.Town;
import domain.model.weather.Weather;
import domain.repository.WeatherRepository;
import domain.service.weather.caching.CachableWeatherService;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/*https://quarkus.io/guides/cache*/

@ApplicationScoped
public class WeatherService implements CachableWeatherService {
    private static final String TOWN_NO_OF_ENTRIES_CACHE = "TOWN_NO_OF_ENTRIES_CACHE";
    private static final String ENTRIES_PER_TOWN_CACHE = "ENTRIES_PER_TOWN_CACHE";

    private final WeatherRepository weatherRepository;

    @Inject
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public List<Town> getCitiesWithMaxEntries(){
        return weatherRepository.getTownsWithMaxWeatherEntries();
    }

    public List<String> getCityNamesWithMaxEntries(){
        return weatherRepository.getTownNamesWithMaxWeatherEntries();
    }

    @CacheResult(cacheName = TOWN_NO_OF_ENTRIES_CACHE)
    public Map<String, Integer> getTownEntriesMap(){
        return weatherRepository.getTownNoOfEntriesMap();
    }

    @CacheResult(cacheName = ENTRIES_PER_TOWN_CACHE)
    public List<Weather> getCountryEntriesList(String country){
        return weatherRepository.getCountryEntriesList(country);
    }

    /* After the first call, Results are cached a default key is calculated (since no parameters in the method)
     * and the results saved in the named cache.*/
    @Override
    public void initializeCityNoOfEnriesCache() {
        getTownEntriesMap();
    }

    @Override
    @CacheInvalidate(cacheName = TOWN_NO_OF_ENTRIES_CACHE)
    /*Clears the cache using the default key since no parameters*/
    public void clearCityNoOfEnriesCache() {
        // implemented by annotation
    }

    /* After the first call, Results are cached a key is calculated will be calculated from the parameters
     * and the results saved in the named cache.*/
    @Override
    public void initializeCountryEntriesCache(String country) {
        getCountryEntriesList(country);
    }

    /*Clears the cache entry for the key calculated from the parameters*/
    @Override
    @CacheInvalidate(cacheName = ENTRIES_PER_TOWN_CACHE)
    public void clearCountryEntriesCache(String country) {
        // implemented by annotation
    }

    /*Clears the cache entries for all the keys in the named cache*/
    @Override
    @CacheInvalidateAll(cacheName = ENTRIES_PER_TOWN_CACHE)
    public void clearAllCountryEntriesCache() {
        // implemented by annotation
    }

    @Override
    public PagedResult<Weather> allPaged(int idx) {
        return weatherRepository.allPaged(idx);
    }

    @Override
    public List<Weather> all() {
        return weatherRepository.all();
    }
}
