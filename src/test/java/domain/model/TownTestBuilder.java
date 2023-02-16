package domain.model;

import domain.model.town.Town;

import java.util.UUID;

public class TownTestBuilder {
    private UUID id = UUID.randomUUID();
    private String name = "Test-Name";
    private String country = "Test-Name";

    public Town build(){
        return new Town(id, name, country);
    }

    public TownTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TownTestBuilder withId(UUID id) {
        this.id = id;
        return this;
    }
}