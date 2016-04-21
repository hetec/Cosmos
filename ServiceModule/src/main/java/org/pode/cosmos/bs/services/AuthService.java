package org.pode.cosmos.bs.services;

import org.pode.cosmos.auth.Authenticator;
import org.pode.cosmos.auth.JwtGenerator;
import org.pode.cosmos.bs.interfaces.AuthServiceLocal;
import org.pode.cosmos.cdi.qualifiers.CosmosCtx;
import org.pode.cosmos.domain.auth.Credentials;
import org.pode.cosmos.domain.entities.UserProfile;
import org.pode.cosmos.domain.exceptions.NoSuchAccountException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotAuthorizedException;

/**
 * Created by patrick on 02.04.16.
 *
 * Enables the registration of a new user through creating a new UserProfile
 * with the provided registration data and persisting it
 */
@Stateless
public class AuthService implements AuthServiceLocal {

    private final static String USERNAME_PARAM = "username";
    private final static long TIME_TO_LIVE_JWT = 1000 * 60; // 1 min

    private EntityManager em;
    private Authenticator authenticator;
    private JwtGenerator jwtGenerator;

    @Inject
    AuthService(@CosmosCtx EntityManager em,
                Authenticator authenticator,
                JwtGenerator jwtGenerator){
        this.em = em;
        this.authenticator = authenticator;
        this.jwtGenerator = jwtGenerator;
    }

    public AuthService(){}

    @Override
    public UserProfile registerUser(Credentials credentials) {
        credentials.setPassword(
                authenticator.hash(
                        credentials.getPassword()
                ));
        UserProfile profile = new UserProfile();
        profile.setUsername(credentials.getUsername());
        profile.setPassword(credentials.getPassword());
        profile.setEmail(credentials.getEmail());
        em.persist(profile);
        return profile;
    }

    public String loginUser(Credentials credentials){
        try {
            TypedQuery<UserProfile> query = em.createNamedQuery(
                    "userCredentials.findByUserName",
                    UserProfile.class);
            query.setParameter(USERNAME_PARAM, credentials.getUsername());
            UserProfile userProfile = query.getSingleResult();
            if(!authenticator.checkCredentials(
                    credentials.getPassword(),
                    userProfile.getPassword())){
                throw new NotAuthorizedException("Not authorized");
            }
            return jwtGenerator.createJwt(userProfile.getUsername(), "Cosomos", TIME_TO_LIVE_JWT);
        } catch (NoResultException noResult){
            throw new NoSuchAccountException(credentials);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NoSuchAccountException(credentials);
            //log cause
        }
    }
}
