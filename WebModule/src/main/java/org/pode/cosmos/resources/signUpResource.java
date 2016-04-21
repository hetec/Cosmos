package org.pode.cosmos.resources;

import org.pode.cosmos.auth.JwtGenerator;
import org.pode.cosmos.bs.interfaces.AuthServiceLocal;
import org.pode.cosmos.domain.auth.Credentials;
import org.pode.cosmos.domain.entities.UserProfile;

import javax.inject.Inject;
import javax.ws.rs.*;
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
    private JwtGenerator jwtGenerator;

    @Inject
    public signUpResource(AuthServiceLocal authService,
                          JwtGenerator jwtGenerator){
        this.authService = authService;
        this.jwtGenerator = jwtGenerator;
    }

    @POST
    public Response registerUser(Credentials credentials, @Context UriInfo uriInfo){
        UserProfile profile = authService.registerUser(credentials);
        URI uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(profile.getId().toString()).build();
        return Response.created(uri).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}
