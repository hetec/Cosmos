package org.pode.cosmos.domain.exceptions;

/**
 * Created by patrick on 23.02.16.
 */
public class NoSuchEntityForIdException extends NoSuchEntityException{

    private Long requestedId;

    public NoSuchEntityForIdException(final Long requestedId){

        super();
        this.requestedId = requestedId;

    }

    public NoSuchEntityForIdException(final Long requestedId, final String msg){
        super(msg);
        this.requestedId = requestedId;
    }

    public Long getRequestedId() {
        return requestedId;
    }

    public void setRequestedId(Long requestedId) {
        this.requestedId = requestedId;
    }
}
