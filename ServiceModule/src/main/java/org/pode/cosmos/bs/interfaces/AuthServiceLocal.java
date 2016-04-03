package org.pode.cosmos.bs.interfaces;

import org.pode.cosmos.domain.auth.Credentials;
import org.pode.cosmos.domain.entities.UserCredentials;

/**
 * Created by patrick on 02.04.16.
 */
public interface AuthServiceLocal {

    UserCredentials registerUser(Credentials credentials);

    boolean authenticate(Credentials credentials);
}
