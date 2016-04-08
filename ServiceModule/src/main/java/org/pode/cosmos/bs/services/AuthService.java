package org.pode.cosmos.bs.services;

import org.pode.cosmos.auth.Authenticator;
import org.pode.cosmos.bs.interfaces.AuthServiceLocal;
import org.pode.cosmos.cdi.qualifiers.CosmosCtx;
import org.pode.cosmos.domain.auth.Credentials;
import org.pode.cosmos.domain.entities.UserCredentials;
import org.pode.cosmos.domain.exceptions.NoSuchAccountException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by patrick on 02.04.16.
 *
 * Enables the registration of a new user through creating a new UserCredentials
 * with the provided registration data and persisting it
 */
@Stateless
public class AuthService implements AuthServiceLocal {

    private final static String USERNAME_PARAM = "username";

    private EntityManager em;
    private Authenticator authenticator;

    @Inject
    AuthService(@CosmosCtx EntityManager em,
                Authenticator authenticator){
        this.em = em;
        this.authenticator = authenticator;
    }

    public AuthService(){}

    @Override
    public UserCredentials registerUser(Credentials credentials) {
        credentials.setPassword(
                authenticator.hash(
                        credentials.getPassword()
                ));
        UserCredentials profile = new UserCredentials();
        profile.setUsername(credentials.getUsername());
        profile.setPassword(credentials.getPassword());
        profile.setEmail(credentials.getEmail());
        em.persist(profile);
        return profile;
    }

    public boolean authenticate(Credentials credentials){
        try {
            TypedQuery<UserCredentials> query = em.createNamedQuery(
                    "userCredentials.findByUserName",
                    UserCredentials.class);
            query.setParameter(USERNAME_PARAM, credentials.getUsername());
            UserCredentials userCredentials = query.getSingleResult();
            return authenticator.checkCredentials(
                    credentials.getPassword(),
                    userCredentials.getPassword());
        } catch (NoResultException noResult){
            throw new NoSuchAccountException(credentials);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NoSuchAccountException(credentials);
            //log cause
        }
    }
}
