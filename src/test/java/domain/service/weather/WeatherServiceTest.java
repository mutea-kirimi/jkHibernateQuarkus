package domain.service.weather;

import domain.model.weather.Current;
import domain.model.weather.Location;
import domain.model.weather.Request;
import domain.model.weather.Weather;
import domain.repository.WeatherRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@QuarkusTest
class WeatherServiceTest {
    @InjectMock
    WeatherRepository weatherRepository;

    @Inject
    WeatherService weatherService;

    @Test
    void shouldCacheValuesCorrectly(){
        //given
        var map = Map.of("MAINZ", 5,
                "NAIROBI", 2);
        when(weatherRepository.getTownNoOfEntriesMap()).thenReturn(map);

        //when
        var result1 = weatherService.getTownEntriesMap();
        var result2 = weatherService.getTownEntriesMap();
        var result3 = weatherService.getTownEntriesMap();

        //then
        assertThat(result1).isEqualTo(result2);
        assertThat(result3).isEqualTo(result2);
        verify(weatherRepository, times(1)).getTownNoOfEntriesMap();
    }

    @Test
    void initialiseCacheIsWorkingCorrectly(){
        //given
        var map = Map.of("MAINZ", 5,
                "NAIROBI", 2);
        when(weatherRepository.getTownNoOfEntriesMap()).thenReturn(map);

        //when
        weatherService.initializeCityNoOfEnriesCache();
        var result1 = weatherService.getTownEntriesMap();
        var result2 = weatherService.getTownEntriesMap();
        var result3 = weatherService.getTownEntriesMap();

        //then
        assertThat(result1).isEqualTo(result2);
        assertThat(result3).isEqualTo(result2);
        verify(weatherRepository, times(1)).getTownNoOfEntriesMap();
    }

    @Test
    void shouldClearCache(){
        //given
        var map = Map.of("MAINZ", 5,
                "NAIROBI", 2);
        when(weatherRepository.getTownNoOfEntriesMap()).thenReturn(map);

        //when
        weatherService.initializeCityNoOfEnriesCache();
        var result1 = weatherService.getTownEntriesMap();
        weatherService.clearCityNoOfEnriesCache();
        var result2 = weatherService.getTownEntriesMap();

        //then
        assertThat(result1).isEqualTo(result2);
        verify(weatherRepository, times(2)).getTownNoOfEntriesMap();
    }

    @Test
    void shouldCacheValuesCorrectlyForCountryEntriesForEachKey(){
        //given
        var list1 = List.of(new Weather(new Request(), new Location(), new Current()));
        var list2 = List.of(new Weather(new Request(), new Location(), new Current()));

        var town1 = "Mainz";
        var town2 = "Nairobi";

        when(weatherRepository.getCountryEntriesList(town1)).thenReturn(list1);
        when(weatherRepository.getCountryEntriesList(town2)).thenReturn(list2);

        //when
        var result1 = weatherService.getCountryEntriesList(town1);
        var result2 = weatherService.getCountryEntriesList(town2);
        var result3 = weatherService.getCountryEntriesList(town1);
        var result4 = weatherService.getCountryEntriesList(town2);

        //then
        assertThat(result1).isEqualTo(result3);
        assertThat(result2).isEqualTo(result4);
        verify(weatherRepository, times(1)).getCountryEntriesList(town1);
        verify(weatherRepository, times(1)).getCountryEntriesList(town2);
    }

    @Test
    void invalidateCacheWorksCorrectlyForCountryEntriesForEachKey(){
        //given
        var list1 = List.of(new Weather(new Request(), new Location(), new Current()));
        var list2 = List.of(new Weather(new Request(), new Location(), new Current()));

        var town1 = "Mainz";
        var town2 = "Nairobi";

        when(weatherRepository.getCountryEntriesList(town1)).thenReturn(list1);
        when(weatherRepository.getCountryEntriesList(town2)).thenReturn(list2);

        //when
        var result1 = weatherService.getCountryEntriesList(town1);
        var result2 = weatherService.getCountryEntriesList(town2);
        weatherService.clearCountryEntriesCache(town1);
        var result3 = weatherService.getCountryEntriesList(town1);
        var result4 = weatherService.getCountryEntriesList(town2);

        //then
        assertThat(result1).isEqualTo(result3);
        assertThat(result2).isEqualTo(result4);
        verify(weatherRepository, times(2)).getCountryEntriesList(town1);
        verify(weatherRepository, times(1)).getCountryEntriesList(town2);
    }

    @Test
    void invalidateCacheWorksCorrectlyForCountryEntriesForAllKey(){
        //given
        var list1 = List.of(new Weather(new Request(), new Location(), new Current()));
        var list2 = List.of(new Weather(new Request(), new Location(), new Current()));

        var town1 = "Mainz";
        var town2 = "Nairobi";

        when(weatherRepository.getCountryEntriesList(town1)).thenReturn(list1);
        when(weatherRepository.getCountryEntriesList(town2)).thenReturn(list2);

        //when
        var result1 = weatherService.getCountryEntriesList(town1);
        var result2 = weatherService.getCountryEntriesList(town2);
        weatherService.clearAllCountryEntriesCache();
        var result3 = weatherService.getCountryEntriesList(town1);
        var result4 = weatherService.getCountryEntriesList(town2);

        //then
        assertThat(result1).isEqualTo(result3);
        assertThat(result2).isEqualTo(result4);
        verify(weatherRepository, times(2)).getCountryEntriesList(town1);
        verify(weatherRepository, times(2)).getCountryEntriesList(town2);
    }
}