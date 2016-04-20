package org.pode.cosmos.bs.interfaces;

import org.pode.cosmos.domain.auth.Credentials;
import org.pode.cosmos.domain.entities.UserProfile;

/**
 * Created by patrick on 02.04.16.
 */
public interface AuthServiceLocal {

    UserProfile registerUser(Credentials credentials);

    String loginUser(Credentials credentials);
}
