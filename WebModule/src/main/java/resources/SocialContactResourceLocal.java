package resources;

import javax.ejb.Local;

/**
 * Created by patrick on 19.02.16.
 */
@Local
public interface SocialContactResourceLocal {

    String getContact(Long id);

}
