package domain.service.town;

import domain.model.town.Town;
import domain.model.weather.Weather;
import domain.repository.TownRepository;
import domain.repository.WeatherRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
* Here is where separated and encapsulated business logic is implemented.Rule of thumb if a logic to be implemented in the
* code requires more than 10 lines or is complicated then create a service for it.
* It will gather all necessary components for its dedicated goal e.g. saving data directly. processing data and then saving,
* retrieving data, retrieving data and processing, ensures no business logic in repository and Resource.
* The service will then be called in the resources or in other services to provide the business logic.
* This enables good testing and separation of concern.
* If there is technology specific aspects required e.g. quartz, apache etc. other than pojo then put a interface in domain layer
* that is then implemented in the application layer.
* */

@ApplicationScoped
@Transactional
public class TownService {
    private final TownRepository townRepository;
    private final WeatherRepository weatherRepository;

    @Inject
    public TownService(TownRepository townRepository, WeatherRepository weatherRepository) {
        this.townRepository = townRepository;
        this.weatherRepository = weatherRepository;
    }

    public Town create(String name, String country ){
        var town = new Town(name.toUpperCase(), country.toUpperCase());
        townRepository.save(town);
        return town;
    }

    public Town get(UUID id){
       return townRepository.findOrFail(id);
    }

    public void delete(Town town){
        townRepository.delete(town.getId());
    }

    public List<Town> list(){
        return townRepository.all();
    }

    public void update(Town town, String name, String country){
        town.setName(name.toUpperCase());
        town.setCountry(country.toUpperCase());
        townRepository.save(town);
    }

    public Town findOrFail(UUID id){
        return townRepository.findOrFail(id);
    }

    public Optional<Town> findByNameAndCountry(String name, String country){
        return townRepository.findByNameAndCountry(name.toUpperCase(), country.toUpperCase());
    }

    public int sumOfChars(String name){
        return name.chars().sum();
    }

    public void addWeatherInstance(Weather weather) {
        weatherRepository.save(weather);
    }

    public void storeWeather(String town, String country, Weather weather){
        var townEntity = this.findByNameAndCountry(town, country);

        if(!townEntity.isPresent()){
            var created = create(town, country);
            this.addWeatherInstance(Weather.toEntity(weather, created.getId()));
        }else{
            this.addWeatherInstance(Weather.toEntity(weather, townEntity.get().getId()));
        }
    }
}
