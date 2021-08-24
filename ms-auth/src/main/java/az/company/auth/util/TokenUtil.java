package az.company.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author MehdiyevCS on 24.08.21
 */
@Slf4j
public final class TokenUtil {
    private static final Clock clock = DefaultClock.INSTANCE;

    private TokenUtil() {
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        var claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JwtConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String generateToken(String username,
                                       List<String> roleList,
                                       Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roleList);
        claims.put("user_id", userId);
        return doGenerateToken(claims, username);
    }

    private static String doGenerateToken(Map<String, Object> claims,
                                          String subject) {
        var createdDate = clock.now();
        var expirationDate = calculateExpirationDate(createdDate);
        log.info("now is {},will be expired at {}", createdDate, expirationDate);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setAudience(JwtConstants.TOKEN_AUDIENCE)
                .setIssuer(JwtConstants.TOKEN_ISSUER)
                .setHeaderParam("typ", JwtConstants.TOKEN_TYPE)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, JwtConstants.JWT_SECRET)
                .compact();
    }

    private static Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + JwtConstants.TOKEN_EXPIRATION);
    }

    public static boolean isTokenValid(String token) {
        if (Objects.isNull(token)) {
            return false;
        }
        return !isTokenExpired(token);
    }

    private static boolean isTokenExpired(String token) {
        var expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(clock.now());
    }

    private static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public static boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JwtConstants.JWT_SECRET)
                    .parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException exception) {
            log.info("Request to parse expired JWT : {} failed : {}", authToken, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.info("Request to parse unsupported JWT : {} failed : {}", authToken, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.info("Request to parse invalid JWT : {} failed : {}", authToken, exception.getMessage());
        } catch (SignatureException exception) {
            log.info("Request to parse JWT with invalid signature : {} failed : {}", authToken, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.info("Request to parse empty or null JWT : {} failed : {}", authToken, exception.getMessage());
        }

        return false;
    }

}

