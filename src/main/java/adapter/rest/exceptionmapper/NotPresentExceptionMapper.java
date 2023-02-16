package adapter.rest.exceptionmapper;

import domain.common.exceptions.NotPresentException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotPresentExceptionMapper implements ExceptionMapper<NotPresentException> {

    @Override
    public Response toResponse(NotPresentException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(exception.getMessage())
                .build();
    }
}
