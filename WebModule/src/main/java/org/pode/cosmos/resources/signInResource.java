package org.pode.cosmos.resources;

import org.pode.cosmos.bs.interfaces.AuthServiceLocal;
import org.pode.cosmos.domain.auth.Credentials;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by patrick on 03.04.16.
 */
@Path("/signIn")
public class signInResource {

    private AuthServiceLocal authService;

    @Inject
    public signInResource(AuthServiceLocal authService){
        this.authService = authService;
    }

    @POST
    public Response signIn(Credentials credentials, @Context UriInfo uriInfo){
        boolean isValidUser = authService.authenticate(credentials);
        return Response.ok("Is valid account: " + isValidUser).build();
    }

}
