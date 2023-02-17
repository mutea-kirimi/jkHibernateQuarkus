package application.jpaRepository;

import domain.common.exceptions.NotPresentException;
import domain.common.paging.PagedResult;
import domain.model.town.Town;
import domain.model.weather.Weather;
import domain.repository.WeatherRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class WeatherJPAService implements WeatherRepository, PanacheRepositoryBase<Weather, UUID> {

    public static final int DEFAULT_PAGE_SIZE = 2;

    @Override
    public void save(Weather weather) {
        this.persist(weather);
    }

    @Override
    public void delete(UUID id) {
        var weather = findOrFail(id);
        this.delete(weather);
    }

    @Override
    public Optional<Weather> find(UUID id) {
        return Optional.ofNullable(this.findById(id));
    }

    @Override
    public Weather findOrFail(UUID id) {
        return this.find(id).orElseThrow(() -> new NotPresentException("The Object does not exist!"));
    }

    @Override
    public PagedResult<Weather> allPaged(int pageIndex) {
        PanacheQuery<Weather> page = this.findAll(Sort.descending("updatedAt")).page(pageIndex, DEFAULT_PAGE_SIZE );
        var total = ((BigInteger) getEntityManager().createNativeQuery(
                "select count(*) from current_weather;"
        ).getSingleResult()).intValue();
        return new PagedResult<Weather>(page.list(), page.pageCount(), DEFAULT_PAGE_SIZE, total);
    }

    /*
    * createNativeQuery() takes in SQL while createQuery() takes in jPQL
    * As second parameters you can only pass Entity Classes.
    * When you want to return anything (select) other than the Entity class or Primitives createQuery() is better Suited.
    * Generally CreateQuery is better as you work with class and property names and you can return any type of object.
    * But in some instances you way want to use native sql.
    * */
    public List<Town> getTownsWithMaxWeatherEntries() {
        return getEntityManager().createNativeQuery(
                       "With agg as (select town_id, count(*) as total " +
                               "             from current_weather " +
                               "             group by town_id) " +
                               "select * from towns " +
                               "where id in (select town_id from agg " +
                               "            where total = (select max(total) from agg));" ,
                Town.class
        ).getResultList();
    }

    public List<String> getTownNamesWithMaxWeatherEntries() {
        return getEntityManager().createNativeQuery(
                "With agg as (select town_id, count(*) as total " +
                        "             from current_weather " +
                        "             group by town_id) " +
                        "select name from towns " +
                        "where id in (select town_id from agg " +
                        "            where total = (select max(total) from agg));"
        ).getResultList();
    }

    public Map<String, Integer> getTownNoOfEntriesMap(){
        return getEntityManager().createQuery(
                "select w.location.name as name, count(w) as total " +
                        "from Weather w " +
                        "group by w.location.name " +
                        "order by total desc ",
                Tuple.class
        ).getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> ((String) tuple.get("name")),
                                tuple -> ((Number) tuple.get("total")).intValue()
                        )
                );
    }

    public List<UUID> getTownIdsWithMaxWeatherEntries() {
        return getEntityManager().createNativeQuery(
                "With agg as (select town_id, count(*) as total " +
                        "             from current_weather " +
                        "             group by town_id) " +
                        "select cast(id as varchar) as id from towns " +
                        "where id in (select town_id from agg " +
                        "            where total = (select max(total) from agg));"
        ).getResultList();
    }

    public List<Weather> getCountryEntriesList(String country){
        return this.find("country = ?1", country).list();
    }

    @Override
    public List<Weather> all() {
        return this.listAll();
    }

}
