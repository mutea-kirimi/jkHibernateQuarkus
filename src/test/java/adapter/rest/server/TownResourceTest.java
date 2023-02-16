package adapter.rest.server;

import adapter.rest.dto.TownDetailsDto;
import domain.common.exceptions.NotPresentException;
import domain.model.TownTestBuilder;
import domain.service.town.TownService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.*;

@QuarkusTest
class TownResourceTest {

    @InjectMock
    TownService townService;

    @Test
    void createWithCorrectNameIsSuccessful(){
        //given
        TownDetailsDto details = new TownDetailsDto("test", "test-country");
        when(townService.findByNameAndCountry(any(),any())).thenReturn(Optional.empty());
        when(townService.create(any(), any())).thenReturn(new TownTestBuilder().build());

        //when
        //then
        given()
                .contentType(ContentType.JSON)
                .body(details)
                .post("api/town")
                .then()
                .statusCode(200);
        verify(townService).create(details.getName(), details.getCountry());
    }

    @Test
    void createWithInvalidNameReturnsBadRequest(){
        //given
        TownDetailsDto details = new TownDetailsDto("t", "country");

        //when
        //then
        given()
                .contentType(ContentType.JSON)
                .body(details)
                .post("api/town")
                .then()
                .statusCode(400);
    }

    @Test
    void listShouldBeSuccessful(){
        //given
        //when
        //then
        given()
                .get("api/town/list")
                .then()
                .statusCode(200);
        verify(townService).list();
    }

    @Test
    void getShouldBeSuccessful(){
        //given
        var id = UUID.randomUUID();
        when(townService.get(any())).thenReturn(new TownTestBuilder().build());
        //when
        //then
        given()
                .get("api/town/{id}", id)
                .then()
                .statusCode(200);
        verify(townService).get(id);
    }

    @Test
    void getShouldReturnResourceNotFoundWhenObjectDoesNotExist(){
        //given
        var id = UUID.randomUUID();
        when(townService.get(any())).thenThrow(new NotPresentException());
        //when
        //then
        given()
                .get("api/town/{id}", id)
                .then()
                .statusCode(404);
        verify(townService).get(id);
    }

    @Test
    void deleteShouldBeSuccessful(){
        //given
        var id = UUID.randomUUID();
        var quarkus = new TownTestBuilder()
                .withId(id)
                .build();
        when(townService.findOrFail(any())).thenReturn(quarkus);
        //when
        //then
        given()
                .delete("api/town/{id}", id)
                .then()
                .statusCode(204);
        verify(townService).delete(quarkus);
    }

    @Test
    void deleteShouldReturnResourceNotFoundWhenItDoesNotExist(){
        //given
        var id = UUID.randomUUID();
        when(townService.findOrFail(any())).thenThrow(new NotPresentException());
        //when
        //then
        given()
                .delete("api/town/{id}", id)
                .then()
                .statusCode(404);
    }

    @Test
    void updateShouldBeSuccessful(){
        //given
        var id = UUID.randomUUID();
        var newName = "new";
        var oldName = "old";
        var dto = new TownDetailsDto(id, newName, "country");
        var town = new TownTestBuilder()
                .withId(id)
                .withName(oldName)
                .build();
        when(townService.findByNameAndCountry(any(),any())).thenReturn(Optional.empty());
        when(townService.findOrFail(any())).thenReturn(town);

        //when
        //then
        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .put("api/town/{id}", id)
                .then()
                .statusCode(204);
        verify(townService).update(town, newName, "country");
    }

    @Test
    void updateShouldReturnResourceNotFoundWhenTheObjectDoesNotExist(){
        //given
        var id = UUID.randomUUID();
        var dto = new TownDetailsDto("newName", "country");
        when(townService.findByNameAndCountry(any(),any())).thenReturn(Optional.empty());
        when(townService.findOrFail(any())).thenThrow(new NotPresentException());

        //when
        //then
        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .put("api/town/{id}", id)
                .then()
                .statusCode(404);
    }
}