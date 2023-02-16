package adapter.rest.dto;

import domain.model.weather.Request;
import domain.model.weather.RequestType;
import domain.model.weather.UnitType;

public record RequestDto(
        RequestType type,
        String query,
        String language,
        UnitType unit
) {

    public static Request toModel(RequestDto dto){
        return new Request(dto.type, dto.query, dto.language, dto.unit);
    }
}
