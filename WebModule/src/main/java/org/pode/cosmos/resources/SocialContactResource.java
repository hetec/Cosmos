package org.pode.cosmos.resources;

import org.pode.cosmos.bs.interfaces.SocialContactService;
import org.pode.cosmos.dataservice.SocialContactDataService;
import org.pode.cosmos.domain.SocialContact;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by patrick on 19.02.16.
 */
@Path("/contacts")
@Produces("application/json")
@Consumes("application/json")
public class SocialContactResource {

    private SocialContactService socialContactService;

    public SocialContactResource(SocialContactService socialContactService){
        this.socialContactService = socialContactService;
    }

    @GET
    @Path("{id}")
    public Response getContact(@PathParam("id") Long id){
        return Response.ok(socialContactService.getSocialContact(id)).build();

    }
}
