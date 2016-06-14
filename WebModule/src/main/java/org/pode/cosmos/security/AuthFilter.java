package org.pode.cosmos.security;

import io.jsonwebtoken.JwtException;
import org.pode.cosmos.cdi.qualifiers.DefaultLocale;
import org.pode.cosmos.domain.exceptions.ApiErrorResponse;
import org.pode.cosmos.domain.exceptions.errors.ApiAuthError;
import org.pode.cosmos.domain.exceptions.errors.ApiError;
import org.pode.cosmos.exceptionHandling.model.ExceptionInfo;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.util.Locale;
import java.util.Objects;

/**
 * Auth filter which checks if a provided token exists and is valid.
 *
 */
@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter{

    private static final String PREFIX = "BEARER";
    private static final int TOKEN_PARTS = 2;
    private static final int TOKEN_INDEX = 1;
    private static final String SEPARATOR = " ";


    private HttpServletRequest request;
    private JwtGenerator jwtGenerator;
    private Locale locale;

    @Inject
    public AuthFilter(final JwtGenerator jwtGenerator,
                      final @DefaultLocale Locale locale,
                      final @Context HttpServletRequest request){
        this.jwtGenerator = jwtGenerator;
        this.locale = locale;
        this.request = request;
    }

    /**
     * Authentication filter to verify the JWT for annotated methods or classes
     *
     * @param requestContext The context of the current request
     * @throws IOException
     */
    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        // Get authorization header
        final String authHeader = requestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(Objects.isNull(authHeader)){
            abortProcess(requestContext, buildDefaultResponse(ApiAuthError.A1006));
        }else{
            if(!authHeader.startsWith(PREFIX)){
                abortProcess(requestContext, buildDefaultResponse(ApiAuthError.A1004));
            }else{
                // Get the token
                final String[] tokenParts = extractToken(authHeader);
                String token;
                if(tokenParts.length == TOKEN_PARTS){
                    token = tokenParts[TOKEN_INDEX];
                    if(Objects.isNull(token) || token.trim().isEmpty()){
                        abortProcess(requestContext, buildDefaultResponse(ApiAuthError.A1006));
                    } else {
                        // Verify the token
                        try{
                            final String subject = jwtGenerator.verifyJwt(token);

                            requestContext.setSecurityContext(new SecurityContext() {
                                @Override
                                public Principal getUserPrincipal() {
                                    return () -> subject;
                                }

                                @Override
                                public boolean isUserInRole(String role) {
                                    return true;
                                }

                                @Override
                                public boolean isSecure() {
                                    return request.isSecure();
                                }

                                @Override
                                public String getAuthenticationScheme() {
                                    return null;
                                }
                            });
                        } catch (JwtException jwtEx){
                            abortProcess(requestContext, buildDefaultResponse(ApiAuthError.A1007));
                        }
                    }
                }else {
                    abortProcess(requestContext, buildDefaultResponse(ApiAuthError.A1005));
                }
            }

        }

    }

    private String[] extractToken(final String authString){
        return authString.split(SEPARATOR);
    }

    private void abortProcess(
            final ContainerRequestContext requestContext,
            final Response response){
        requestContext.abortWith(response);
    }

    private Response buildDefaultResponse(final ApiError error){
        return Response
                .status(error.getStatus())
                .entity(new ApiErrorResponse(error, locale))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
