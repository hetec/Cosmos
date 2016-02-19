package org.pode.cosmos.dataservice;

import org.pode.cosmos.domain.SocialContact;

import javax.persistence.EntityManager;

/**
 * Created by patrick on 19.02.16.
 */
public class SocailContactDbDataService implements SocialContactDataService {

    private EntityManager entityManager;

    public SocailContactDbDataService(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public SocialContact findById(Long id) {
        return entityManager.find(SocialContact.class, id);
    }
}
