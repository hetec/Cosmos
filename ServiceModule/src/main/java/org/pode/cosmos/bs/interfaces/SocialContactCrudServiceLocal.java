package org.pode.cosmos.bs.interfaces;

import org.pode.cosmos.domain.SocialContact;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by patrick on 19.02.16.
 */
@Local
public interface SocialContactCrudServiceLocal {

    SocialContact findById(Long id);

    List<SocialContact> findAll();

    SocialContact save(SocialContact socialContact);

    SocialContact update(SocialContact socialContact);

}
