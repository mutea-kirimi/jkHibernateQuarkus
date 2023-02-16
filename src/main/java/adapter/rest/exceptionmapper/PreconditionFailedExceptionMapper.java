package adapter.rest.exceptionmapper;

import domain.common.exceptions.PreconditionFailedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PreconditionFailedExceptionMapper implements ExceptionMapper<PreconditionFailedException> {

    @Override
    public Response toResponse(PreconditionFailedException exception) {
        return Response.status(Response.Status.PRECONDITION_FAILED.getStatusCode(), exception.getMessage()).build();
    }
}
