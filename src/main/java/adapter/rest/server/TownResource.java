package adapter.rest.server;

import adapter.rest.dto.TownDetailsDto;
import adapter.rest.dto.TownDto;
import domain.common.exceptions.NotPresentException;
import domain.model.town.Town;
import domain.service.town.TownService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/api/town/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class TownResource {
    // The below method provides the default Quarkus logger
    private static final Logger LOG = LoggerFactory.getLogger(TownResource.class);
    private final TownService townService;

    public TownResource(TownService townService) {
        this.townService = townService;
    }

    @POST
    @Transactional
    public TownDto create(TownDetailsDto details){
        LOG.info("POST: New Quarkus Object has been created with the name /{}", details.getName());
        ensureNameAndCountryHaveMinimumThreeCharachters(details.getName(), details.getCountry());
        ensureUniqueCombinationOfTownNameAndCountry(details.getName(), details.getCountry());

        try {
            var town = townService.create(details.getName(), details.getCountry());
            return TownDto.toDto(town);
        } catch (Exception e) {
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR);
        } finally {
            // runs both when Successful or Exception thrown!
        }
    }

   @GET
   @Path("{id}")
   @Transactional
   public TownDto getTown(@PathParam("id") UUID id){
        LOG.info("GET : Quarkus with id /{} has been fetched", id);

        var town = townService.get(id);
        return TownDto.toDto(town);

   }

    @PUT
    @Path("{id}")
    @Transactional
    public void updateTown(@PathParam("id") UUID id, TownDetailsDto details){
        LOG.info("PUT : Quarkus with id /{} has been updated to name : /{}", id, details.getName());
        ensureNameAndCountryHaveMinimumThreeCharachters(details.getName(), details.getCountry());
        ensureUniqueCombinationOfTownNameAndCountry(details.getName(), details.getCountry());

        var town = ensuretownExists(id);
        townService.update(town, details.getName(), details.getCountry());
    }

   @GET
   @Path("list")
   @Transactional
   public List<TownDto> list(){
       LOG.info("Get : fetch all Quarkus in Database");

       return townService.list().stream()
               .map(x -> TownDto.toDto(x))
               .collect(Collectors.toList());
   }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") UUID id){
        LOG.info("DELETE : Quarkus with id /{} is to be deleted", id);
        var town = ensuretownExists(id);

        townService.delete(town);
    }

    private void ensureNameAndCountryHaveMinimumThreeCharachters(String name, String country) {
        if (name == null || name.length() < 3 || country == null || country.length() < 3) {
            throw new IllegalArgumentException("The name requires a minimum of 3 charachters!");
        }
    }

    private Town ensuretownExists(UUID id) throws NotPresentException {
        return townService.findOrFail(id);
    }

    private void ensureUniqueCombinationOfTownNameAndCountry(String name, String country) {
        if (townService.findByNameAndCountry(name, country).isPresent()){
            throw new IllegalArgumentException("The town already exists");
        };
    }
}
