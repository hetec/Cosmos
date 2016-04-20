package org.pode.cosmos.exceptions.handler;

import org.pode.cosmos.exceptions.model.ExceptionInfo;

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
        ExceptionInfo info = new ExceptionInfo(
                exception.getClass().getName(),
                exception.getMessage(),
                "Unexpected");
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(info)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
