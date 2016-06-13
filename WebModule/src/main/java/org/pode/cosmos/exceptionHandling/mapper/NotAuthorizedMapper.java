package org.pode.cosmos.exceptionHandling.mapper;

import org.pode.cosmos.domain.exceptions.ApiErrorResponse;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps NotAuthorizedExceptions to appropriate responses
 */
@Provider
public class NotAuthorizedMapper implements ExceptionMapper<NotAuthorizedException>{

    @Override
    public Response toResponse(NotAuthorizedException exception) {
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(new ApiErrorResponse(exception, Response.Status.UNAUTHORIZED))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}
