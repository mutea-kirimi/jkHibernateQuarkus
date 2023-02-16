package adapter.rest.dto;

import java.util.UUID;

public class TownDetailsDto {
    private UUID id;
    private String name;
    private String country;

    public TownDetailsDto() {
    }

    public TownDetailsDto(UUID id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public TownDetailsDto(String name, String country) {
        this(null, name, country);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "TownDetailsDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
