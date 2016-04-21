package org.pode.cosmos.auth;

import io.jsonwebtoken.*;
import org.pode.cosmos.cdi.qualifiers.SystemZonedClock;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotAuthorizedException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * Class to generate and verify JWTs
 */
@Named
@RequestScoped
public class JwtGenerator {

    private final static SignatureAlgorithm ALGO = SignatureAlgorithm.HS512;
    private final static String TIME_ZONE = "CET";

    private ApiKey apiKey;
    private Clock clock;

    public JwtGenerator(){}

    @Inject
    public JwtGenerator(@SystemZonedClock Clock clock,
                        ApiKey apiKey){
        this.apiKey = apiKey;
        this.clock = clock;
    }

    /**
     * Generates a JWT
     * @param subject The subject of the token
     * @param issuer The issuer of the token
     * @param timeToLiveMs Time until the token gets expired
     * @return A JWT
     */
    public String createJwt(final String subject,
                            final String issuer,
                            final long timeToLiveMs){

        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(ALGO, apiKey.getSecret());

        LocalDateTime ldt = LocalDateTime.now(clock).plus(timeToLiveMs, ChronoUnit.MILLIS);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(TIME_ZONE));
        long millis = zdt.toInstant().toEpochMilli();
        builder.setExpiration(new Date(millis));

        return builder.compact();
    }

    /**
     * Checks the validity of a given JWT
     *
     * @param jwt The Json Web Token
     * @return Subject of the tokens payload
     * @throws NotAuthorizedException if any Exception occurs
     */
    public String verifyJwt(final String jwt) throws JwtException{
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(apiKey.getSecret())
                    .parseClaimsJws(jwt).getBody();
            if(Objects.isNull(claims)) {
                throw new JwtException("Claims and header must not be null");
            }
        } catch (ExpiredJwtException expiredEx) {
            throw new JwtException("Expired token");
        } catch (JwtException jwtEx){
            throw new JwtException("Invalid token");
        }

        return claims.getSubject();

    }
}
