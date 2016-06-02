package org.pode.cosmos.exceptionHandling.model;

import javax.ws.rs.core.Response;

/**
 * Created by patrick on 01.06.16.
 */
public interface ApiError {

    Response.Status getStatus();

    String getMessageKey();

    String getErrorCode();

}
