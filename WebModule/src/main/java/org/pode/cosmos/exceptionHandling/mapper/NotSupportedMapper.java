package org.pode.cosmos.exceptionHandling.mapper;

import org.pode.cosmos.domain.exceptions.ApiErrorResponse;

import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps NotSupportedExceptions to appropriate responses
 */
@Provider
public class NotSupportedMapper implements ExceptionMapper<NotSupportedException>{

    @Override
    public Response toResponse(javax.ws.rs.NotSupportedException exception) {
        return Response.status(Response
                .Status.UNSUPPORTED_MEDIA_TYPE)
                .entity(new ApiErrorResponse(exception, Response.Status.UNSUPPORTED_MEDIA_TYPE))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
