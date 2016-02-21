package org.pode.cosmos.bs.interfaces;

import org.pode.cosmos.cdi.qualifiers.CosmosCtx;
import org.pode.cosmos.domain.SocialContact;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by patrick on 19.02.16.
 */
@Stateless
public class SocialContactService implements SocialContactServiceLocal{
//    @Inject @CosmosCtx
    private EntityManager em;

    public SocialContactService() {}

    @Inject
    public SocialContactService(@CosmosCtx EntityManager em){
        this.em = em;
    }

    @Override
    public SocialContact getSocialContact(Long id) {
        return em.find(SocialContact.class, id);
    }

    @Override
    public SocialContact save(SocialContact socialContact) {
       return null;
    }
}
