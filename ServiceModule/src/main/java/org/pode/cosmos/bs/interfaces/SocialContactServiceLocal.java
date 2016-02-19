package org.pode.cosmos.bs.interfaces;

import org.pode.cosmos.domain.SocialContact;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by patrick on 19.02.16.
 */
@Local
public interface SocialContactServiceLocal {

    SocialContact getSocialContact(Long id);

}
