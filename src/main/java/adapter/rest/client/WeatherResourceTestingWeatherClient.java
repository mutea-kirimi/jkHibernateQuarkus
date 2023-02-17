package adapter.rest.client;

import adapter.rest.dto.WeatherDto;
import domain.common.exceptions.NotPresentException;
import domain.common.paging.PagedResult;
import domain.model.town.Town;
import domain.model.weather.Weather;
import domain.service.ExcelExporterService;
import domain.service.town.TownService;
import domain.service.weather.WeatherImportService;
import domain.service.weather.WeatherService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Inject
    ExcelExporterService<Weather> weatherExcelExporterService;

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
        return weatherService.allPaged(idx);
    }

    @GET
    @Path("list/excel-export")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response export() {
       try{
           var excelResult = weatherExcelExporterService.export();
           var suffix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
           return Response.ok(new ByteArrayInputStream(excelResult.toByteArray()))
                   .header("Content-Disposition", "attachment; filename=\"weather("+suffix+").xlsx\"")
                   .build();
       }catch (Exception e){
           throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR);
       }
    }


    private void ensureCityIsAvailable(WeatherDto dto){
        if(dto.current() == null){
            throw new NotPresentException("There is no such city");
        }
    }
}
