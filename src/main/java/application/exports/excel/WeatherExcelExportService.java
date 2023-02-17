package application.exports.excel;

import application.exports.ExcelExporter;
import application.exports.Field;
import domain.model.weather.Weather;
import domain.service.weather.WeatherService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

@ApplicationScoped
public class WeatherExcelExportService extends ExcelExporter<Weather> {
    private WeatherService weatherService;

    @Inject
    public WeatherExcelExportService(WeatherService weatherService) {
        this();
        this.weatherService = weatherService;
    }

    public WeatherExcelExportService() {
        super(
                new Field.StringField<>("Name", (weather) -> weather.getLocation().getName()),
                new Field.StringField<>("Country", (weather) -> weather.getLocation().getCountry()),
                new Field.StringField<>("Region", (weather) -> weather.getLocation().getRegion()),
                new Field.DoubleField<>("Latitude", (weather) -> weather.getLocation().getLat()),
                new Field.DoubleField<>("Longitude", (weather) -> weather.getLocation().getLon()),
                new Field.StringField<>("Timezone", (weather) -> weather.getLocation().getTimezone_id()),
                new Field.DateTimeField<>("Time", (weather -> weather.getLocation().getLocalDateTime())),
                new Field.StringField<>("Weather-Descriptions", (weather) -> {
                    return weather.getCurrent().getWeather_descriptions().stream()
                            .collect(Collectors.joining(", "));
                }),
                new Field.IntegerField<>("Wind-Speed", (weather -> weather.getCurrent().getWind_speed())),
                new Field.IntegerField<>("Wind-Degree", (weather -> weather.getCurrent().getWind_degree())),
                new Field.IntegerField<>("Humidity", (weather -> weather.getCurrent().getHumidity())),
                new Field.IntegerField<>("Temprature", (weather -> weather.getCurrent().getTemprature())),
                new Field.IntegerField<>("Precipitation", (weather -> weather.getCurrent().getPrecip())),
                new Field.IntegerField<>("Pressure", (weather -> weather.getCurrent().getPressure()))
        );
    }

    public ByteArrayOutputStream export() throws IOException {
        return super.export(weatherService.all());
    }
}
