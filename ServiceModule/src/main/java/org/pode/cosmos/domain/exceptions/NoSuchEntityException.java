package org.pode.cosmos.domain.exceptions;

/**
 * Created by patrick on 23.02.16.
 */
public class NoSuchEntityException extends RuntimeException{

    private static final String DEFAULT_MSG = "The requested entity does not exist";

    public NoSuchEntityException(){
        super(DEFAULT_MSG);
    }

    public NoSuchEntityException(final String msg){
        super(msg);
    }

}
