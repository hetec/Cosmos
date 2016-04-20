package org.pode.cosmos.auth;

import io.jsonwebtoken.*;
import sun.misc.BASE64Encoder;

import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Base64;
import java.util.Objects;

/**
 * Created by patrick on 18.04.16.
 */
@Named
@RequestScoped
public class JwtGenerator {

    SignatureAlgorithm ALGO = SignatureAlgorithm.HS512;
    String KEY = "TEST";

    public String createJwt(final String subject,
                            final String issuer,
                            final long timeToLiveMs){

        String jwt = Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(ALGO, KEY)
                .compact();

        return jwt;
    }

    public String verifyJwt(final String jwt){
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(jwt)
                    .getBody();
            if(Objects.isNull(claims)){
                throw new JwtException("Claims must not be null");
            }
        }catch (JwtException jwtEx){
            throw new NotAuthorizedException("Invalid token");
        }

        return claims.getSubject();
    }

}
