package org.pode.cosmos.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
    private static final long TIME_TO_LIVE = 1000 * 60;


    private JwtGenerator jwtGenerator;

    @Inject
    public AuthFilter(JwtGenerator jwtGenerator){
        this.jwtGenerator = jwtGenerator;
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
        if(!authHeader.startsWith(PREFIX)){
            abortProcess(requestContext, buildDefaultResponse("Starts not with 'BEARER'"));
        }
        // Get the token
        final String[] tokenParts = extractToken(authHeader);
        String token;
        if(tokenParts.length == TOKEN_PARTS){
            token = tokenParts[TOKEN_INDEX];
            if(Objects.isNull(token) || token.trim().isEmpty()){
                abortProcess(requestContext, buildDefaultResponse("Token does not exist"));
            } else {
                // Verify the token
                try{
                    jwtGenerator.verifyJwt(token);
                } catch (JwtException jwtEx){
                    abortProcess(requestContext, buildDefaultResponse(jwtEx.getMessage()));
                }
            }
        }else {
            abortProcess(requestContext, buildDefaultResponse("Invalid separator or format"));
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

    private Response buildDefaultResponse(final String reason){
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(new ExceptionInfo(
                        AuthFilter.class.getSimpleName(),
                        reason,
                        "USE -> 'BEARER token' in the authorization header"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
