package domain.service.weather.caching;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/* you can use this class also as a central point to govern the caching*/
@ApplicationScoped
public class WeatherCachingService {
    @Inject
    Instance<CachableWeatherService> cachableWeatherServiceInstances;

    public void clearAllCaches(){
        cachableWeatherServiceInstances.forEach(i -> {
            i.clearAllCountryEntriesCache();
            i.clearCityNoOfEnriesCache();
        });
    }

    public void initializeCountryEntriesCache(String country){
        cachableWeatherServiceInstances.forEach(i -> {
            i.initializeCountryEntriesCache(country);
        });
    }

    //...etc
}
