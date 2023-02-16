package adapter.rest.client.weather;

import adapter.rest.dto.WeatherDto;
import domain.common.exceptions.WeatherApiResponseException;
import domain.service.weather.WeatherImportService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/*
* Service to make the api. Implements a interface in Domain layer
* */
@Singleton
public class WeatherAPIClientService implements WeatherImportService<WeatherDto> {

    @Inject
    WeatherApiKeyProvider apiKeyProvider;

    @RestClient
    @Inject
    WeatherProxyClient proxyClient;

    private static final Logger LOG = LoggerFactory.getLogger(WeatherAPIClientService.class);

    @Override
    public WeatherDto getCurrentWeatherForCity(String city) throws WeatherApiResponseException{
        LOG.info("An API call has been made for the current weather for the city of : {}.", city);

        var API_KEY = apiKeyProvider.getApiKey();
        return proxyClient.get(API_KEY, city);
    }
}
