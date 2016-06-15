package org.pode.cosmos.exceptionHandling.interceptors;

import org.pode.cosmos.cdi.qualifiers.DefaultLocale;
import org.pode.cosmos.domain.exceptions.ApiException;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.util.Locale;

/**
 * Created by patrick on 01.06.16.
 */
public class ApiExceptionInterceptor {

    @Inject
    private @DefaultLocale Locale locale;

    @AroundInvoke
    public Object handleException(InvocationContext invocationContext){
        Object returnedResponse;
        try {
            returnedResponse = invocationContext.proceed();
        } catch (Exception e) {
            Response errorResponse;
            if(e instanceof ApiException){
                errorResponse = ((ApiException)e).getResponse();
            }else if(e.getCause() instanceof ApiException){
                errorResponse = ((ApiException)e.getCause()).getResponse();
            }else {
                errorResponse = (new ApiException(e)).getResponse();
            }
            return errorResponse;
        }
        return returnedResponse;
    }
}
