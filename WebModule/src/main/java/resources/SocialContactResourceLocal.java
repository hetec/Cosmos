package resources;

import org.pode.cosmos.domain.SocialContact;

import javax.ejb.Local;

/**
 * Created by patrick on 19.02.16.
 */
@Local
public interface SocialContactResourceLocal {

    String getContact(Long id);

    void saveContact(SocialContact socialContact);

}
