package org.pode.cosmos.resources;

import org.pode.cosmos.bs.interfaces.AuthServiceLocal;
import org.pode.cosmos.domain.auth.Credentials;
import org.pode.cosmos.domain.entities.UserCredentials;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by patrick on 02.04.16.
 */
@Path("/signUp")
@Consumes("application/json")
@Produces("application/json")
public class signUpResource {

    private AuthServiceLocal authService;

    @Inject
    public signUpResource(AuthServiceLocal authService){
        this.authService = authService;
    }

    @POST
    public Response registerUser(Credentials credentials, @Context UriInfo uriInfo){
        UserCredentials profile = authService.registerUser(credentials);

        URI uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(profile.getId().toString()).build();
        return Response.created(uri).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}
