package org.pode.cosmos.resources;

import com.sun.org.glassfish.external.probe.provider.annotations.Probe;
import org.pode.cosmos.bs.interfaces.RegistrationServiceLocal;
import org.pode.cosmos.domain.auth.RegistrationData;
import org.pode.cosmos.domain.entities.UserProfile;

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
@Path("/registration")
@Consumes("application/json")
@Produces("application/json")
public class RegistrationResource {

    private RegistrationServiceLocal registrationService;

    @Inject
    public RegistrationResource(RegistrationServiceLocal registrationService){
        this.registrationService = registrationService;
    }

    @POST
    public Response registerUser(RegistrationData registrationData, @Context UriInfo uriInfo){
        UserProfile profile = registrationService.registerUserProfile(registrationData);

        URI uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(profile.getId().toString()).build();
        return Response.created(uri).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}
