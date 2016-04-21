package org.pode.cosmos.resources;

import org.pode.cosmos.annotations.Secured;
import org.pode.cosmos.auth.ApiKey;
import org.pode.cosmos.auth.JwtGenerator;
import org.pode.cosmos.bs.interfaces.AuthServiceLocal;
import org.pode.cosmos.domain.auth.Credentials;

import javax.inject.Inject;
import javax.mail.Header;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

/**
 * Created by patrick on 03.04.16.
 */
@Path("/signIn")
public class signInResource {

    private AuthServiceLocal authService;

    @Inject
    public signInResource(AuthServiceLocal authService,
                          JwtGenerator jwtGenerator){
        this.authService = authService;
    }

    @POST
    public Response signIn(Credentials credentials, @HeaderParam("X-Auth") String auth, @Context UriInfo uriInfo) throws IOException {
        String jwt = authService.loginUser(credentials);
        return Response.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }


}
