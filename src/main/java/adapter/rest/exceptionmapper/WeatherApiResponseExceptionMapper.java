package adapter.rest.exceptionmapper;

import domain.common.exceptions.WeatherApiResponseException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WeatherApiResponseExceptionMapper implements ExceptionMapper<WeatherApiResponseException> {
    @Override
    public Response toResponse(WeatherApiResponseException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Response.Status.INTERNAL_SERVER_ERROR +" :: "+ exception.getMessage())
                .build();
    }
}
