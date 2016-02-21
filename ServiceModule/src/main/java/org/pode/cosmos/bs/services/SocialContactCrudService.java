package org.pode.cosmos.bs.services;

import org.pode.cosmos.bs.interfaces.SocialContactCrudServiceLocal;
import org.pode.cosmos.cdi.qualifiers.CosmosCtx;
import org.pode.cosmos.domain.SocialContact;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by patrick on 19.02.16.
 */
@Stateless
public class SocialContactCrudService implements SocialContactCrudServiceLocal {
    private EntityManager em;

    public SocialContactCrudService() {}

    @Inject
    public SocialContactCrudService(@CosmosCtx EntityManager em){
        this.em = em;
    }

    @Override
    public SocialContact findById(Long id) {
        return em.find(SocialContact.class, id);
    }

    @Override
    public SocialContact save(SocialContact socialContact) {
        em.persist(socialContact);
        return socialContact;
    }
}
