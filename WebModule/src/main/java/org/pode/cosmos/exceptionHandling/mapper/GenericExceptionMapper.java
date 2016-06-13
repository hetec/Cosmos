package org.pode.cosmos.exceptionHandling.mapper;

import org.pode.cosmos.domain.exceptions.ApiErrorResponse;
import org.pode.cosmos.exceptionHandling.model.ExceptionInfo;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by patrick on 23.02.16.
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

    @Override
    public Response toResponse(Throwable exception) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ApiErrorResponse(exception))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
