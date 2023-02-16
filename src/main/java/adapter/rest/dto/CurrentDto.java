package adapter.rest.dto;

import domain.model.weather.Current;

import java.util.Set;

public record CurrentDto(
        int temperature,
        int weather_code,
        Set<String> weather_icons,
        Set<String> weather_descriptions,
        int wind_speed,
        int wind_degree,
        String wind_dir,
        int pressure,
        int precip,
        int humidity,
        int cloudcover,
        int feelslike,
        int uv_index
) {
    public static Current toModel(CurrentDto dto){
        return new Current(
               dto.temperature,
                dto.weather_code,
                dto.weather_icons,
                dto.weather_descriptions,
                dto.wind_speed,
                dto.wind_degree,
                dto.wind_dir,
                dto.pressure,
                dto.precip,
                dto.humidity,
                dto.cloudcover,
                dto.feelslike,
                dto.uv_index
        );
    }
}
