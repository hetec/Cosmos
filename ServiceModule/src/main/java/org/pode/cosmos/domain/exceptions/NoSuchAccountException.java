package org.pode.cosmos.domain.exceptions;

import org.pode.cosmos.domain.auth.Credentials;

/**
 * Created by patrick on 03.04.16.
 */
public class NoSuchAccountException extends RuntimeException{

    private static final String DEFAULT_MSG = "No user account for the given credentials";
    private Credentials credentials = null;

    public NoSuchAccountException(){
        super(DEFAULT_MSG);
    }

    public NoSuchAccountException(final Credentials credentials){
        super(DEFAULT_MSG);
        this.credentials = credentials;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
