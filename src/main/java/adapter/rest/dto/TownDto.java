package adapter.rest.dto;

import domain.model.town.Town;

import java.util.UUID;

public record TownDto(
        UUID id,
        String name,
        String country
) {
    public TownDto {
    }

    public static TownDto toDto(Town town) {
        return new TownDto(
                town.getId(),
                town.getName(),
                town.getCountry()
        );
    }
}
