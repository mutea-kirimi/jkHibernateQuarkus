package application.jpaRepository;

import com.DatabaseTestResource;
import domain.model.town.Town;
import domain.model.TownTestBuilder;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/*
* The most important annotation in a Quarkus test is the @QuarkusTest annotation. This annotation is used to start the Quarkus application on port 8081
* */
@QuarkusTest
/*
*  Used to define a test resource and in this case a database instance to be used for testing
* */
@QuarkusTestResource(DatabaseTestResource.class)
class TownJPARepositoryTest {

    @Inject
    TownJPARepository repository;

    @BeforeEach
    void prepare() {
        repository.deleteAll();
    }

    @Test
    void save() {
        // given
        Town town = new TownTestBuilder()
                .build();

        // when
        repository.save(town);

        // then
        var q = repository.find(town.getId()).orElseThrow();
        assertThat(q.getId()).isEqualTo(town.getId());
    }

    @Test
    void update(){
        //given
        var id = UUID.randomUUID();
        var oldName = "old";
        var newName = "new";
        Town oldTown = new TownTestBuilder()
                .withId(id)
                .withName(oldName)
                .build();
        repository.save(oldTown);

        //when
        Town newTown = new TownTestBuilder()
                .withId(id)
                .withName(newName)
                .build();
        repository.update(newTown);

        //then
        var q = repository.find(id).orElseThrow();
        assertThat(q.getName()).isEqualTo(newName);
    }

}