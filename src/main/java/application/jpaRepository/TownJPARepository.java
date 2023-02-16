package application.jpaRepository;

import domain.common.exceptions.NotPresentException;
import domain.model.town.Town;
import domain.repository.TownRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class TownJPARepository implements TownRepository, PanacheRepositoryBase<Town, UUID> {

    @Override
    public void save(Town town) {
        this.persist(town);
    }

    @Override
    public void delete(UUID id) {
        this.delete(id);
    }

    @Override
    public void update(Town town) {
        find(town.getId()).ifPresent(t -> {
            t.setName(town.getName());
            t.setCountry(town.getCountry());
            persist(t);
        });
    }

    @Override
    public Optional<Town> find(UUID id) {
        return Optional.ofNullable(this.findById(id));
    }

    @Override
    public Town findOrFail(UUID id) {
        return this.find(id).orElseThrow(() -> new NotPresentException("There is no such Element with this Id "+id));
    }

    @Override
    public Optional<Town> findByNameAndCountry(String name, String country) {
        return Optional.ofNullable(this.find("name = ?1 and country = ?2", name, country).firstResult());
    }

    @Override
    public List<Town> all() {
        return this.listAll();
    }
}
