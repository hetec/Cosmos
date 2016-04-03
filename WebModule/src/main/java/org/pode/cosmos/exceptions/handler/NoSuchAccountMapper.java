package org.pode.cosmos.exceptions.handler;

import org.pode.cosmos.domain.exceptions.NoSuchAccountException;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by patrick on 03.04.16.
 */
@Provider
@Produces("application/json")
public class NoSuchAccountMapper implements ExceptionMapper<NoSuchAccountException>{

    @Override
    public Response toResponse(NoSuchAccountException exception) {
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(exception.getCredentials())
                .build();
    }
}
