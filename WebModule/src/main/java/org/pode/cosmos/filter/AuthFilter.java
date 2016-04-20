package org.pode.cosmos.filter;

import com.sun.xml.internal.ws.client.RequestContext;
import org.pode.cosmos.annotations.Secured;
import org.pode.cosmos.auth.JwtGenerator;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by patrick on 18.04.16.
 */
@Provider
@Secured
public class AuthFilter implements ContainerRequestFilter{

    private JwtGenerator jwtGenerator;

    @Inject
    public AuthFilter(JwtGenerator jwtGenerator){
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Get authorization header
        final String authHeader = requestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(!authHeader.startsWith("BEARER")){
            abortProcess(requestContext, "Starts not with 'BEARER'");
        }
        // Get the token
        final String[] tokenParts = extractToken(authHeader);
        String token;
        if(tokenParts.length == 2){
            token = tokenParts[1];
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
        return authString.split(" ");
    }

    private void abortProcess(ContainerRequestContext requestContext, String reason){
        requestContext.abortWith(Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(reason)
                .build());
    }
}
