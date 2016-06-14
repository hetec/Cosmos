package org.pode.cosmos.domain.exceptions.errors;

import javax.ws.rs.core.Response;

/**
 * Collection of authentication errors
 *
 * A1001 - Wrong password
 * A1002 - Account not found
 * A1003 - Account already existing
 * A1004 - Missing BEARER key word
 * A1005 - Invalid token format
 * A1006 - Missing token
 * A1007 - Invalid token
 */
public enum ApiAuthError implements ApiError{

    A1001(Response.Status.UNAUTHORIZED, "auth_failed_login"),
    A1002(Response.Status.NOT_FOUND, "auth_not_found_for_username"),
    A1003(Response.Status.BAD_REQUEST, "auth_account_already_exists"),
    A1004(Response.Status.UNAUTHORIZED, "auth_token_must_start_with_bearer"),
    A1005(Response.Status.UNAUTHORIZED, "auth_invalid_token_format"),
    A1006(Response.Status.UNAUTHORIZED, "auth_missing_token"),
    A1007(Response.Status.UNAUTHORIZED, "auth_invalid_token");

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
