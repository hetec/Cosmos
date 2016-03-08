package org.pode.cosmos.resources;

import org.pode.cosmos.bs.interfaces.SocialContactCrudServiceLocal;
import org.pode.cosmos.bs.services.SocialContactCrudService;
import org.pode.cosmos.domain.entities.SocialContact;
import org.pode.cosmos.domain.entities.Traits;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * Created by patrick on 19.02.16.
 */
@RequestScoped
@Path("/contacts")
@Produces("application/json")
@Consumes("application/json")
public class SocialContactResource{

    SocialContactCrudServiceLocal socialContactCrudService;

    @Inject
    public SocialContactResource(SocialContactCrudServiceLocal socialContactCrudService){
        this.socialContactCrudService = socialContactCrudService;
    }

    public SocialContactResource(){}

    @GET
    public Response getAllContacts(){
        List<SocialContact> allContacts = socialContactCrudService.findAll();
        GenericEntity<List<SocialContact>> list = new GenericEntity<List<SocialContact>>(allContacts){};
        return Response
                .ok(list)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @GET
    @Path("{id}")
    public Response getContact(@PathParam("id") Long id){
        SocialContact socialContact = socialContactCrudService.findById(id);
        return Response
                .ok(socialContact)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @POST
    public Response saveContact(SocialContact socialContact, @Context UriInfo uriInfo){
        SocialContact persistedContact = socialContactCrudService.save(socialContact);
        URI uri = uriInfo.getBaseUriBuilder()
                .path(SocialContactResource.class)
                .path(persistedContact.getId().toString())
                .build();
        return Response
                .created(uri)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updateContact(SocialContact socialContact,
                                  @PathParam("id") Long id){
        socialContact.setId(id);
        SocialContact updatedContact = socialContactCrudService.update(socialContact);
        return Response
                .ok(updatedContact)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @DELETE
    @Consumes("*/*")
    @Path("{id}")
    public Response deleteContact(@PathParam("id") Long id){
        SocialContact removedContact = socialContactCrudService.delete(id);
        return Response
                .ok(removedContact)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}
