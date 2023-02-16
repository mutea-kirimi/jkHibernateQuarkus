package application.scheduling.cronjobs;

import adapter.rest.dto.WeatherDto;
import domain.service.town.TownService;
import domain.service.weather.WeatherImportService;
import io.quarkus.scheduler.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class AutomaticWeatherUpdate {

    private static final Logger LOG = LoggerFactory.getLogger(AutomaticWeatherUpdate.class);

    private TownService townService;
    private WeatherImportService<WeatherDto> weatherImportService;

    @Inject
    public AutomaticWeatherUpdate(TownService townService, WeatherImportService<WeatherDto> weatherImportService) {
        this.townService = townService;
        this.weatherImportService = weatherImportService;
    }

    @Transactional
    @Scheduled(cron = "{scheduler.cron.weather-update}", identity = "weatherUpdate")
    public void run() {
        // exception handling / logging, done by quarkus-scheduler.
        LOG.info("{} started update", getClass());
        var storedTowns = townService.list();

        storedTowns.forEach(town -> {
            var weatherDto = (WeatherDto) weatherImportService.getCurrentWeatherForCity(town.getName());

            if(weatherDto.location() == null){
                LOG.warn("The API client servise is unavailable for the town : {}.", town.getName());
            }else{
                var townQueried = weatherDto.location().name();
                var countryQueried = weatherDto.location().country();

                townService.storeWeather(townQueried, countryQueried, WeatherDto.toModel(weatherDto));
                LOG.info("Updated Weather for Town : {} : successfully.", town.getName());
            }
        });

        LOG.info("{} successfully finished update", getClass());
    }
}
