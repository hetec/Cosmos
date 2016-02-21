package org.pode.cosmos.bs.interfaces;

import org.pode.cosmos.domain.SocialContact;

import javax.ejb.Local;

/**
 * Created by patrick on 19.02.16.
 */
@Local
public interface SocialContactServiceLocal {

    SocialContact getSocialContact(Long id);

    SocialContact save(SocialContact socialContact);

}
