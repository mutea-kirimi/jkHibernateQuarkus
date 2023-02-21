package adapter.rest.client;

import adapter.rest.dto.WeatherDto;
import application.LogService;
import domain.common.exceptions.NotPresentException;
import domain.common.paging.PagedResult;
import domain.model.town.Town;
import domain.model.weather.Weather;
import domain.service.CsvExporterService;
import domain.service.ExcelExporterService;
import domain.service.town.TownService;
import domain.service.weather.WeatherImportService;
import domain.service.weather.WeatherService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.nio.charset.StandardCharsets;
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
    ExcelExporterService<Weather> excelExporterService;
    @Inject
    CsvExporterService<Weather> csvExporterService;
    @Inject
    LogService logService;

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
    public Response exportExcel() {
       try{
           var excelResult = excelExporterService.export();
           var suffix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
           return Response.ok(new ByteArrayInputStream(excelResult.toByteArray()))
                   .header("Content-Disposition", "attachment; filename=\"weather("+suffix+").xlsx\"")
                   .build();
       }catch (Exception e){
           throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR);
       }
    }

    @GET
    @Path("list/csv-export")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response exportCsv() {
        var resultString = csvExporterService.export();
        try{
            var bytes = resultString.getBytes(StandardCharsets.ISO_8859_1);
            return Response.ok(new ByteArrayInputStream(bytes))
                    .header("Content-Disposition", "attachment; filename=\"weatherCSV.csv\"")
                    .build();
        }catch (Exception e){
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/logs")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Transactional
    public Response logs() {
        return Response
                .ok(new ByteArrayInputStream(logService.getLogFileContent()))
                .header("Content-Disposition", "attachment; filename=\"" + "logs_" + LocalDateTime.now() + ".log" + "\"")
                .build();
    }


    private void ensureCityIsAvailable(WeatherDto dto){
        if(dto.current() == null){
            throw new NotPresentException("There is no such city");
        }
    }
}
