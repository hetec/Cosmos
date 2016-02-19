package org.pode.cosmos.bs.interfaces;

import org.pode.cosmos.dataservice.SocialContactDataService;
import org.pode.cosmos.domain.SocialContact;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by patrick on 19.02.16.
 */
public class SocialContactService implements SocialContactServiceLocal{

    @PersistenceContext
    private EntityManager entityManager;

    private SocialContactDataService socialContactDataService;


    @Override
    public SocialContact getSocialContact(Long id) {
        return socialContactDataService.findById(id);
    }
}
