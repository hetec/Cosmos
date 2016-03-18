package org.pode.cosmos.bs.services;

import org.pode.cosmos.bs.interfaces.SocialContactCrudServiceLocal;
import org.pode.cosmos.cdi.qualifiers.CosmosCtx;
import org.pode.cosmos.domain.entities.SocialContact;
import org.pode.cosmos.domain.entities.Traits;
import org.pode.cosmos.domain.exceptions.NoSuchEntityForIdException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

/**
 * Created by patrick on 19.02.16.
 */
@Stateless
public class SocialContactCrudService implements SocialContactCrudServiceLocal {

    private static final String ERROR_MSG_DELETE = "The social contact you want to delete does not exist";
    private static final String ERROR_MSG_UPDATE = "The social contact you want to update does not exist";
    private static final String ERROR_MSG_FIND = "The social contact for the requested id does not exist";

    private EntityManager em;

    public SocialContactCrudService() {}

    @Inject
    public SocialContactCrudService(@CosmosCtx EntityManager em){
        this.em = em;
    }

    @Override
    public SocialContact findById(Long id) throws NoSuchEntityForIdException{
        SocialContact result = em.find(SocialContact.class, id);
        if(!Objects.nonNull(result)){
            throw new NoSuchEntityForIdException(id, ERROR_MSG_FIND);
        }
        return result;
    }

    @Override
    public List<SocialContact> findAll() {
        TypedQuery<SocialContact> query =
                em.createNamedQuery("socialContact.findAll",
                                    SocialContact.class);

        return query.getResultList();

    }

    @Override
    public SocialContact save(SocialContact socialContact) {
        em.persist(socialContact);
        return socialContact;
    }

    @Override
    public SocialContact update(SocialContact socialContact) {
        SocialContact existingContact = em.find(SocialContact.class, socialContact.getId());
        if(existingContact == null){
            throw new NoSuchEntityForIdException(socialContact.getId(), ERROR_MSG_UPDATE);
        }
        return em.merge(socialContact);
    }

    @Override
    public SocialContact delete(Long id) {
        SocialContact removedContact = em.find(SocialContact.class, id);
        try {
            em.remove(removedContact);
        }catch (IllegalArgumentException exception){
            throw new NoSuchEntityForIdException(id, ERROR_MSG_DELETE);
        }
        return removedContact;
    }
}
