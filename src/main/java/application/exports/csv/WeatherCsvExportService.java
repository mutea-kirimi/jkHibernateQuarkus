package application.exports.csv;

import application.exports.CsvExporter;
import application.exports.CsvField;
import domain.common.exceptions.NotAllowedException;
import domain.model.weather.Weather;
import domain.service.weather.WeatherService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@ApplicationScoped
public class WeatherCsvExportService extends CsvExporter<Weather> {

    @ConfigProperty(name = "hibernate-demo.feature.csvExport", defaultValue = "false")
    private boolean csvExportEnabled;

    private WeatherService weatherService;

    @Inject
    public WeatherCsvExportService(WeatherService weatherService) {
        this();
        this.weatherService = weatherService;
    }

    public WeatherCsvExportService() {
        super(
                new CsvField<>("Name", weather -> weather.getLocation().getName()),
                new CsvField<>("Country", weather -> weather.getLocation().getCountry()),
                new CsvField<>("Region", weather -> weather.getLocation().getRegion()),
                new CsvField<>("Latitude", weather -> weather.getLocation().getLat()+""),
                new CsvField<>("Longitude", weather -> weather.getLocation().getLon()+""),
                new CsvField<>("Timezone", weather -> weather.getLocation().getTimezone_id()),
                new CsvField<>("Time", weather -> {
                    var format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    return weather.getLocation().getLocalDateTime().format(format);
                }),
                new CsvField<>("Weather-Descriptions", weather -> {
                    return weather.getCurrent().getWeather_descriptions().stream()
                            .collect(Collectors.joining(", "));
                }),
                new CsvField<>("Wind-Speed", weather -> weather.getCurrent().getWind_speed()+""),
                new CsvField<>("Wind-Degree", weather -> weather.getCurrent().getWind_degree()+""),
                new CsvField<>("Humidity", weather -> weather.getCurrent().getHumidity()+""),
                new CsvField<>("Temprature", weather -> weather.getCurrent().getTemprature()+""),
                new CsvField<>("Pressure", weather -> weather.getCurrent().getPressure()+""),
                new CsvField<>("Precipitation", weather -> weather.getCurrent().getPrecip()+"")
        );
    }

    @Override
    public String export(){
        validateIfFeatureIsEnabled();
        return this.export(weatherService.all());
    }

    /*
    * In the above Method if the number of elements are many .. we could try to introduce batched processing of the results
    * in that we use paged results to get elements from the database instead of loading all Elements at once into memory and
    * process them in a for loop after knowing the number of elements in the DB and hence the number of pages. and it is possible
    * to order them.
    */

    public void validateIfFeatureIsEnabled(){
        if(!csvExportEnabled){
            throw new NotAllowedException("This Feature has not been enabled!");
        }
    }

}
