package org.pode.cosmos.bs.interfaces;

import org.pode.cosmos.domain.auth.RegistrationData;
import org.pode.cosmos.domain.entities.UserProfile;

/**
 * Created by patrick on 02.04.16.
 */
public interface RegistrationServiceLocal {

    UserProfile registerUserProfile(RegistrationData registrationData);

}
