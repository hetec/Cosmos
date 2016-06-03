package org.pode.cosmos.exceptionHandling.mapper;

import org.pode.cosmos.domain.exceptions.NoSuchEntityForIdException;
import org.pode.cosmos.exceptionHandling.model.ExceptionInfo;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by patrick on 23.02.16.
 */
@Provider
public class NoSuchEntityForIdMapper implements ExceptionMapper<NoSuchEntityForIdException>{

    @Override
    public Response toResponse(NoSuchEntityForIdException exception) {

        final String addInfo = "The entity with the id #" + exception.getRequestedId() + " does not exist";

        ExceptionInfo info = new ExceptionInfo(
                exception.getClass().getName(),
                exception.getMessage(),
                addInfo);
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(info)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
