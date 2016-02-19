package org.pode.cosmos.resources;

import org.pode.cosomos.domain.SocialContact;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by patrick on 19.02.16.
 */
@Path("/contacts")
@Produces("application/json")
@Consumes("application/json")
public class SocialContactResource {

    @GET
    @Path("{id}")
    public Response getContact(@PathParam("id") Long id){
        SocialContact contact = new SocialContact();
        contact.setId(id);
        contact.setFirstName("John");
        contact.setLastName("Doe");
        return Response.ok(contact).build();

    }
}
