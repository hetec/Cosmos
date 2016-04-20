package org.pode.cosmos.exceptions.handler;

import org.pode.cosmos.exceptions.model.ExceptionInfo;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by patrick on 20.04.16.
 */
@Provider
public class NotAuthorizedMapper implements ExceptionMapper<NotAuthorizedException>{

    @Override
    public Response toResponse(NotAuthorizedException exception) {
        ExceptionInfo info = new ExceptionInfo(
                exception.getClass().getName(),
                exception.getMessage(),
                "use -> 'BEARER token' in the authorization header");
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(info)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}
