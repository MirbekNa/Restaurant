package peaksoft.config;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
@Component
public class JWTService {
    @Value("${spring.jwt.secret.key}")
    private String SECRET_KEY;
    public String generateToken(UserDetails userDetails){
        return com.auth0.jwt.JWT.create()
                .withClaim("username",userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(ZonedDateTime.now().plusDays(1).toInstant()))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }
    public String validateToken(String token){
        JWTVerifier jwtVerifier =
                com.auth0.jwt.JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        DecodedJWT verify = jwtVerifier.verify(token);
        return verify.getClaim("username").asString();
    }
}
