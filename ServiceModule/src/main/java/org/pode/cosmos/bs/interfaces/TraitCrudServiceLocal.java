package org.pode.cosmos.bs.interfaces;

import org.pode.cosmos.domain.entities.SocialContact;
import org.pode.cosmos.domain.entities.Traits;

import java.util.List;
import java.util.Set;

/**
 * Created by patrick on 16.03.16.
 */
public interface TraitCrudServiceLocal {

    Traits findById(Long traitId, Long contactId);

    Set<Traits> findAll(Long contactId);

    Traits save(Traits trait, Long contactId);

    Traits update(Traits trait, Long contactId);

    Traits delete(Long traitId, Long contactId);

}
