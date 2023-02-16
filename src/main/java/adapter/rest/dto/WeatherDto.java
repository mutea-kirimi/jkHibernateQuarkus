package adapter.rest.dto;

import domain.model.weather.Weather;

public record WeatherDto(
        RequestDto request,
        LocationDto location,
        CurrentDto current
){
    public static Weather toModel(WeatherDto dto){
        return new Weather(
              RequestDto.toModel(dto.request),
              LocationDto.toModel(dto.location),
              CurrentDto.toModel(dto.current)
        );
    }
}
