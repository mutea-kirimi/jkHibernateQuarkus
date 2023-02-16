package adapter.rest.exceptionmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public class UnhandledExceptionMapper implements ExceptionMapper<Exception> {
    private static final Logger LOG = LoggerFactory.getLogger(UnhandledExceptionMapper.class);

    @Override
    public Response toResponse(Exception e) {
        if (e instanceof ClientErrorException) {
            LOG.debug("encountered unhandled client error exception in endpoint, responding with HTTP 400", e);
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }
        LOG.error("encountered unhandled server error exception in endpoint, responding with HTTP 500", e);
        return Response
                .serverError()
                .entity("{\"errorCode\":\"UNEXPECTED_SERVER_ERROR_SEE_LOGS\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
