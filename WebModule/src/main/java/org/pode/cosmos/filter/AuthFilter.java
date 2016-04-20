package org.pode.cosmos.filter;

import org.pode.cosmos.annotations.Secured;
import org.pode.cosmos.auth.JwtGenerator;
import org.pode.cosmos.exceptions.model.ExceptionInfo;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Objects;

/**
 * Auth filter which checks if a provided token exists and is valid.
 *
 */
@Provider
@Secured
public class AuthFilter implements ContainerRequestFilter{

    private static final String PREFIX = "BEARER";
    private static final int TOKEN_PARTS = 2;
    private static final int TOKEN_INDEX = 1;
    private static final String SEPARATOR = " ";


    private JwtGenerator jwtGenerator;

    @Inject
    public AuthFilter(JwtGenerator jwtGenerator){
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        // Get authorization header
        final String authHeader = requestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(!authHeader.startsWith(PREFIX)){
            abortProcess(requestContext, "Starts not with 'BEARER'");
        }
        // Get the token
        final String[] tokenParts = extractToken(authHeader);
        String token;
        if(tokenParts.length == TOKEN_PARTS){
            token = tokenParts[TOKEN_INDEX];
            if(Objects.isNull(token) || token.trim().isEmpty()){
                abortProcess(requestContext, "Token does not exist");
            } else {
                // Verify the token
                try{
                    jwtGenerator.verifyJwt(token);
                }catch (NotAuthorizedException authEx){
                    abortProcess(requestContext, authEx.getMessage());
                }
            }
        }else {
            abortProcess(requestContext, "Invalid separator or format");
        }
    }

    private String[] extractToken(final String authString){
        return authString.split(SEPARATOR);
    }

    private void abortProcess(final ContainerRequestContext requestContext, final String reason){
        requestContext.abortWith(Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(new ExceptionInfo(
                        AuthFilter.class.getSimpleName(),
                        reason,
                        "use -> 'BEARER token' in the authorization header"))
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}
