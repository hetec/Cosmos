package org.pode.cosmos.exceptionHandling.mapper;


import org.pode.cosmos.domain.exceptions.ApiErrorResponse;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps NotFoundExceptions to appropriate responses
 */
@Provider
@Produces("application/json")
public class NotFoundMapper implements ExceptionMapper<NotFoundException>{

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new ApiErrorResponse(exception, Response.Status.NOT_FOUND))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
