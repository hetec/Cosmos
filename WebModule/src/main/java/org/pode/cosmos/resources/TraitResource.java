package org.pode.cosmos.resources;

import org.pode.cosmos.bs.interfaces.TraitCrudServiceLocal;
import org.pode.cosmos.bs.services.TraitCrudService;
import org.pode.cosmos.domain.entities.SocialContact;
import org.pode.cosmos.domain.entities.Traits;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Set;

/**
 * Created by patrick on 16.03.16.
 */
@Path("/")
@Consumes("application/json")
@Produces("application/json")
public class TraitResource {

    private Long contactId;
    private TraitCrudServiceLocal traitService;

    public TraitResource() {
    }

    public TraitResource(TraitCrudServiceLocal traitService, Long contactId){
        this.contactId = contactId;
        this.traitService = traitService;
    }

    @GET
    public Response getAllTraits(){
        Set<Traits> traits = traitService.findAll(contactId);
        GenericEntity<Set<Traits>> traitsList = new GenericEntity<Set<Traits>>(traits){};
        return Response
                .ok(traitsList)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    public Response saveTrait(Traits trait, @Context UriInfo uriInfo){
        Traits persitedTrait = traitService.save(trait, contactId);
        URI uri = uriInfo.getBaseUriBuilder()
                .path(SocialContactResource.class)
                .path(persitedTrait.getId().toString())
                .build();
        return Response
                .created(uri)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @GET
    @Path("{id}")
    public Response getTrait(@PathParam("id") Long traitId){
        Traits trait = traitService.findById(traitId, contactId);
        return Response
                .ok(trait)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updateTrait(
            Traits trait,
            @PathParam("id") Long traitId){
        trait.setId(traitId);
        Traits updatedTrait = traitService.update(trait, contactId);
        return Response
                .ok(updatedTrait)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteTrait(@PathParam("id") Long traitId){
        Traits removedTrait = traitService.delete(traitId, contactId);
        return Response
                .ok(removedTrait)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
