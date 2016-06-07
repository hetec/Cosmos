package org.pode.cosmos.domain.exceptions.errors;

import javax.ws.rs.core.Response;

/**
 * Created by patrick on 07.06.16.
 */
public enum ApiAuthError implements ApiError{

    A1001(Response.Status.UNAUTHORIZED, "auth_failed_login"),
    A1002(Response.Status.NOT_FOUND, "auth_not_found_for_username");

    private Response.Status status;
    private String messageKey;

    ApiAuthError(final Response.Status status, final String messageKey) {
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
