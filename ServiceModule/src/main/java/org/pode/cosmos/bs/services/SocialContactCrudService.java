package org.pode.cosmos.bs.services;

import org.pode.cosmos.bs.interfaces.SocialContactCrudServiceLocal;
import org.pode.cosmos.cdi.qualifiers.CosmosCtx;
import org.pode.cosmos.cdi.qualifiers.DefaultLocale;
import org.pode.cosmos.domain.entities.SocialContact;
import org.pode.cosmos.domain.entities.Traits;
import org.pode.cosmos.domain.exceptions.ApiException;
import org.pode.cosmos.domain.exceptions.ApiSocialContactError;
import org.pode.cosmos.domain.exceptions.NoSuchEntityForIdException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by patrick on 19.02.16.
 */
@Stateless
public class SocialContactCrudService implements SocialContactCrudServiceLocal {

    private EntityManager em;
    private Locale locale;

    public SocialContactCrudService() {}

    @Inject
    public SocialContactCrudService(@CosmosCtx EntityManager em, @DefaultLocale Locale locale){
        this.em = em;
        this.locale = locale;
    }

    @Override
    public SocialContact findById(Long id) throws NoSuchEntityForIdException{
        SocialContact result = em.find(SocialContact.class, id);
        if(!Objects.nonNull(result)){
            throw new ApiException(ApiSocialContactError.SC10001, locale);
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
            throw new ApiException(ApiSocialContactError.SC10001, locale);
        }
        return em.merge(socialContact);
    }

    @Override
    public SocialContact delete(Long id) {
        SocialContact removedContact = em.find(SocialContact.class, id);
        try {
            em.remove(removedContact);
        }catch (IllegalArgumentException exception){
            throw new ApiException(ApiSocialContactError.SC10001, locale);
        }
        return removedContact;
    }
}
