package resources;

import org.pode.cosmos.bs.interfaces.SocialContactCrudServiceLocal;
import org.pode.cosmos.domain.SocialContact;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

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
    @Path("{id}")
    public Response getContact(@PathParam("id") Long id){
        SocialContact socialContact = socialContactCrudService.findById(id);
        return Response.ok(socialContact).build();
    }

    @POST
    public Response saveContact(SocialContact socialContact, @Context UriInfo uriInfo){
        SocialContact persistedContact = socialContactCrudService.save(socialContact);
        URI uri = uriInfo.getBaseUriBuilder().path(SocialContactResource.class).path(persistedContact.getId().toString()).build();
        return Response.created(uri).build();
    }



}
