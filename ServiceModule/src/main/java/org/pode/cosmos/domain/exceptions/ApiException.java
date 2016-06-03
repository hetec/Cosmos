package org.pode.cosmos.domain.exceptions;

import javax.ws.rs.core.Response;
import java.util.Locale;

/**
 * Created by patrick on 02.06.16.
 */
public class ApiException extends RuntimeException {

    private static final String HTTP_HEADER_X_ERROR = "X-Error";
    private static final String HTTP_HEADEr_X_ERROR_CODE = "X-Error-Code";

    private Response response;

    public ApiException(final ApiError apiError, final Locale locale){
        this.response = this.buildHttpResponse(
                new ApiErrorResponse(
                        apiError,
                        locale
                ));
    }

    public ApiException(final Exception exception, final Locale locale){
        this.response = this.buildHttpResponse(
                new ApiErrorResponse(
                        exception,
                        locale
                ));
    }

    public ApiException(
            final String msgKey,
            final String errorCode,
            final Response.Status status,
            final Locale locale){
        this.response = this.buildHttpResponse(
                new ApiErrorResponse(
                        status,
                        msgKey,
                        errorCode,
                        locale
                ));
    }

    public Response getResponse() {
        return response;
    }

    private Response buildHttpResponse(final ApiErrorResponse apiErrorResponse){
        return Response
                .status(apiErrorResponse.getStatus())
                .entity(apiErrorResponse)
                .header(HTTP_HEADER_X_ERROR, apiErrorResponse.getErrorMessage())
                .header(HTTP_HEADEr_X_ERROR_CODE, apiErrorResponse.getErrorCode())
                .build();
    }

}
