package application.exports.excel;

import application.exports.ExcelExporter;
import application.exports.ExcelField;
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
                new ExcelField.StringField<>("Name", (weather) -> weather.getLocation().getName()),
                new ExcelField.StringField<>("Country", (weather) -> weather.getLocation().getCountry()),
                new ExcelField.StringField<>("Region", (weather) -> weather.getLocation().getRegion()),
                new ExcelField.DoubleField<>("Latitude", (weather) -> weather.getLocation().getLat()),
                new ExcelField.DoubleField<>("Longitude", (weather) -> weather.getLocation().getLon()),
                new ExcelField.StringField<>("Timezone", (weather) -> weather.getLocation().getTimezone_id()),
                new ExcelField.DateTimeField<>("Time", (weather -> weather.getLocation().getLocalDateTime())),
                new ExcelField.StringField<>("Weather-Descriptions", (weather) -> {
                    return weather.getCurrent().getWeather_descriptions().stream()
                            .collect(Collectors.joining(", "));
                }),
                new ExcelField.IntegerField<>("Wind-Speed", (weather -> weather.getCurrent().getWind_speed())),
                new ExcelField.IntegerField<>("Wind-Degree", (weather -> weather.getCurrent().getWind_degree())),
                new ExcelField.IntegerField<>("Humidity", (weather -> weather.getCurrent().getHumidity())),
                new ExcelField.IntegerField<>("Temprature", (weather -> weather.getCurrent().getTemprature())),
                new ExcelField.IntegerField<>("Precipitation", (weather -> weather.getCurrent().getPrecip())),
                new ExcelField.IntegerField<>("Pressure", (weather -> weather.getCurrent().getPressure()))
        );
    }

    public ByteArrayOutputStream export() throws IOException {
        return super.export(weatherService.all());
    }
}
