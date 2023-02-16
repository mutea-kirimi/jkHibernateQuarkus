package adapter.rest.client;

import adapter.rest.dto.WeatherDto;
import domain.common.exceptions.NotPresentException;
import domain.common.paging.PagedResult;
import domain.model.town.Town;
import domain.model.weather.Weather;
import domain.service.town.TownService;
import domain.service.weather.WeatherImportService;
import domain.service.weather.WeatherService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/*
* Resource to test the client!
* */
@Path("/weather")
@Transactional
public class WeatherResourceTestingWeatherClient {

    @Inject
    WeatherImportService<WeatherDto> weatherImportService;
    @Inject
    TownService townService;
    @Inject
    WeatherService weatherService;

    @GET
    @Path("{city}")
    @Produces(MediaType.APPLICATION_JSON)
    public WeatherDto getCurrentWeather(@PathParam("city") String city) {
        var weatherDto = (WeatherDto) weatherImportService.getCurrentWeatherForCity(city);

        ensureCityIsAvailable(weatherDto);

        var townQueried = weatherDto.location().name();
        var countryQueried = weatherDto.location().country();
        townService.storeWeather(townQueried, countryQueried, WeatherDto.toModel(weatherDto));

        return weatherDto;
    }

    @GET
    @Path("/max")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Town> getCityWithMaxEntries() {
        return weatherService.getCitiesWithMaxEntries();
    }

    @GET
    @Path("/max-tittle")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getCityNamesWithMaxEntries() {
        return weatherService.getCityNamesWithMaxEntries();
    }

    @GET
    @Path("/map")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> getCityEntries() {
        return weatherService.getTownEntriesMap();
    }

    @GET
    @Path("list/{index}")
    @Produces(MediaType.APPLICATION_JSON)
    public PagedResult<Weather> all(@PathParam("index") int idx) {
        return weatherService.all(idx);
    }


    private void ensureCityIsAvailable(WeatherDto dto){
        if(dto.current() == null){
            throw new NotPresentException("There is no such city");
        }
    }
}
