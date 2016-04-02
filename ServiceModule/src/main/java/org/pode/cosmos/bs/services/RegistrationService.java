package org.pode.cosmos.bs.services;

import org.pode.cosmos.auth.Authenticator;
import org.pode.cosmos.bs.interfaces.RegistrationServiceLocal;
import org.pode.cosmos.cdi.qualifiers.CosmosCtx;
import org.pode.cosmos.domain.auth.RegistrationData;
import org.pode.cosmos.domain.entities.UserProfile;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by patrick on 02.04.16.
 *
 * Enables the registration of a new user through creating a new UserProfile
 * with the provided registration data and persisting it
 */
@Stateless
public class RegistrationService implements RegistrationServiceLocal{

    private EntityManager em;
    private Authenticator authenticator;

    @Inject
    RegistrationService(@CosmosCtx EntityManager em,
                        Authenticator authenticator){
        this.em = em;
        this.authenticator = authenticator;
    }

    public RegistrationService(){}

    @Override
    public UserProfile registerUserProfile(RegistrationData registrationData) {
        try {
            registrationData.setPassword(
                    authenticator.createPwHash(
                            registrationData.getPassword()
                    ));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        UserProfile profile = new UserProfile(registrationData);
        em.persist(profile);
        return profile;
    }
}
