package org.pode.cosmos.domain.exceptions.errors;

import javax.ws.rs.core.Response;

/**
 * Created by patrick on 03.06.16.
 */
public enum ApiTraitError implements ApiError {

    T10001(Response.Status.NOT_FOUND, "trait_not_found_for_id"),
    T10002(Response.Status.CONFLICT, "invalid_social_contact_id_for_trait");

    private Response.Status status;
    private String messageKey;

    ApiTraitError(
            final Response.Status status,
            final String messageKey) {
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
