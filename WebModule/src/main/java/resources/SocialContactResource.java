package resources;

import org.pode.cosmos.bs.interfaces.SocialContactServiceLocal;
import org.pode.cosmos.domain.SocialContact;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by patrick on 19.02.16.
 */
@RequestScoped
@Path("/contacts")
@Produces("application/json")
@Consumes("application/json")
public class SocialContactResource {

    SocialContactServiceLocal socialContactService;

    @Inject
    public SocialContactResource(SocialContactServiceLocal socialContactService){
        this.socialContactService = socialContactService;
    }

    public SocialContactResource(){}

    @GET
    @Path("{id}")
    public Response getContact(@PathParam("id") Long id){
        SocialContact socialContact = socialContactService.getSocialContact(id);
        return Response.ok(socialContact).build();
    }


}
