package org.pode.cosmos.exceptionHandling.mapper;

import com.sun.org.glassfish.external.probe.provider.annotations.ProbeProvider;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Maps NotFoundExceptions to appropriate responses
 */
@ProbeProvider
public class NotFoundMapper implements ExceptionMapper<NotFoundException>{

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(exception.getMessage())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
