package org.pode.cosmos.exceptionHandling.mapper;

import org.pode.cosmos.exceptionHandling.model.ExceptionInfo;

import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by patrick on 24.02.16.
 */
@Provider
public class NotSupportedMediaTypeMapper implements ExceptionMapper<NotSupportedException>{

    @Override
    public Response toResponse(NotSupportedException exception) {

        final String addInfo = "Supported media types: application/json";

        ExceptionInfo info = new ExceptionInfo(
                exception.getClass().getName(),
                exception.getMessage(),
                addInfo);
        return Response.status(Response
                .Status.UNSUPPORTED_MEDIA_TYPE)
                .entity(info)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
