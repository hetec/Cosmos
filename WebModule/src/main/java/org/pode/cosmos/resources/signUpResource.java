package org.pode.cosmos.resources;

import org.pode.cosmos.cdi.qualifiers.DefaultLocale;
import org.pode.cosmos.bs.interfaces.AuthServiceLocal;
import org.pode.cosmos.domain.auth.Credentials;
import org.pode.cosmos.domain.entities.UserProfile;
import org.pode.cosmos.exceptionHandling.interceptors.ApiExceptionInterceptor;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Locale;

/**
 * Created by patrick on 02.04.16.
 */
@Path("/signUp")
@Consumes("application/json")
@Produces("application/json")
@Interceptors({ApiExceptionInterceptor.class})
public class signUpResource {

    private AuthServiceLocal authService;

    @Inject
    public signUpResource(AuthServiceLocal authService){
        this.authService = authService;
    }

    @Inject
    private @DefaultLocale
    Locale locale;

    @POST
    public Response registerUser(@Valid Credentials credentials, @Context UriInfo uriInfo){
        UserProfile profile = authService.registerUser(credentials);
        URI uri = uriInfo
                .getBaseUriBuilder()
                .path(ProfileResource.class)
                .path(profile.getId().toString())
                .build();
        return Response.created(uri).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}
