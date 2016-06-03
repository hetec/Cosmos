package org.pode.cosmos.domain.exceptions.errors;

import org.pode.cosmos.domain.exceptions.errors.ApiError;

import javax.ws.rs.core.Response;

/**
 * Created by patrick on 01.06.16.
 */
public enum ApiSocialContactError implements ApiError {

    SC10001(Response.Status.BAD_REQUEST, "social_contact_not_found_for_id");

    private Response.Status status;
    private String messageKey;

    ApiSocialContactError(Response.Status status, String messageKey) {
        this.status = status;
        this.messageKey = messageKey;
    }


    @Override
    public Response.Status getStatus() {
        return this.status;
    }

    @Override
    public String getMessageKey() {
        return this.messageKey;
    }

    @Override
    public String getErrorCode() {
        return this.name();
    }
}
