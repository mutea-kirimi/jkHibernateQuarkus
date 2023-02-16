package domain.repository;

import domain.model.town.Town;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TownRepository {
    void save(Town town);
    void delete(UUID id);
    void update(Town town);
    Optional<Town> find(UUID id);
    Town findOrFail(UUID id);
    Optional<Town> findByNameAndCountry(String name, String Country);
    List<Town> all();
}
