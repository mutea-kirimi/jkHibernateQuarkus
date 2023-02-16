package domain.service;

import domain.model.town.Town;
import domain.repository.TownRepository;
import domain.repository.WeatherRepository;
import domain.service.town.TownService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TownServiceTest {
    @Mock
    TownRepository townRepository;

    @Mock
    WeatherRepository weatherRepository;

    TownService service;

    @BeforeEach
    /* We do this  when we want to avoid the @Quarkustest annotation ie the application need not be started for
    * the tests to work*/
    void prepare() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        service = new TownService(townRepository, weatherRepository);
    }

    @Test
    void sumOfCharsShouldReturnCorrectValues(){
        //given
        var s = "adcd";

        //when
        var result = service.sumOfChars(s);

        //then
        assertThat(result).isEqualTo(396);
        verifyNoInteractions(townRepository);
    }

    @Test
    void findOrFailShouldDelegateToRepository(){
        //given
        var id = UUID.randomUUID();
        when(townRepository.findOrFail(any())).thenReturn(mock(Town.class));

        //when
        var res = service.findOrFail(id);

        //then
        verify(townRepository).findOrFail(id);
        verifyNoMoreInteractions(townRepository);
    }
}