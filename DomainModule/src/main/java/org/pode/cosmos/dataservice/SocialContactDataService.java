package org.pode.cosmos.dataservice;

import org.pode.cosmos.domain.SocialContact;

/**
 * Created by patrick on 19.02.16.
 */
public interface SocialContactDataService {

    SocialContact findById(Long id);

}
