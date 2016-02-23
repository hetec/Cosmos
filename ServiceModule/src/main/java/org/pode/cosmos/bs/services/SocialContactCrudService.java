package org.pode.cosmos.bs.services;

import org.pode.cosmos.bs.interfaces.SocialContactCrudServiceLocal;
import org.pode.cosmos.cdi.qualifiers.CosmosCtx;
import org.pode.cosmos.domain.SocialContact;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

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
        if(em.find(SocialContact.class, socialContact.getId()) == null){
            throw new IllegalArgumentException("Social contact id does not exist!");
        }
        return em.merge(socialContact);
    }
}
