package org.pode.cosmos.domain.exceptions;


import org.pode.cosmos.domain.exceptions.errors.ApiError;
import org.pode.cosmos.domain.exceptions.jaxbAdapters.ResponseStatusAdapter;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by patrick on 01.06.16.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ApiErrorResponse {

    final static private String RESOURCE_BUNDLE_BASENAME = "apiErrorMessages";

    @XmlJavaTypeAdapter(ResponseStatusAdapter.class)
    private Response.Status status;
    private String errorMessage;
    private String errorCode;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(
            final Response.Status status,
            final String msgKey,
            final String errorCode,
            final Locale locale) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = getLocalizedErrorMsg(msgKey, locale);
    }

    public ApiErrorResponse(
            final ApiError apiError,
            final Locale locale) {
        this.status = apiError.getStatus();
        this.errorCode = apiError.getErrorCode();
        this.errorMessage = getLocalizedErrorMsg(apiError, locale);
    }

    public ApiErrorResponse(
            final Exception exception,
            final Locale locale) {
        this.status = Response.Status.INTERNAL_SERVER_ERROR;
        this.errorCode = "0";
        this.errorMessage = exception.getLocalizedMessage();
    }

    public Response.Status getStatus() {
        return status;
    }

    public void setStatus(Response.Status status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private String getLocalizedErrorMsg(
            final ApiError apiError,
            final Locale locale){
        final ResourceBundle bundle = ResourceBundle.getBundle(
                RESOURCE_BUNDLE_BASENAME,
                locale);
        return bundle.getString(apiError.getMessageKey());
    }

    private String getLocalizedErrorMsg(
            final String msgKey,
            final Locale locale){
        final ResourceBundle bundle = ResourceBundle.getBundle(
                RESOURCE_BUNDLE_BASENAME,
                locale);
        return bundle.getString(msgKey);
    }
}
