package az.company.cardorder.security;

import az.company.cardorder.config.properties.TokenProperties;
import az.company.cardorder.security.constants.JwtConstants;
import az.company.cardorder.util.FormatterUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.DefaultClock;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author MehdiyevCS on 24.08.21
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TokenCreator {

    private static final Clock clock = DefaultClock.INSTANCE;
    private final TokenProperties tokenProperties;
    private Key key;

    @PostConstruct
    private void init() {
        byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.getBase64Secret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        var claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(String username,
                                List<String> roleList,
                                Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roleList);
        claims.put("user_id", userId);
        return doGenerateToken(claims, username);
    }

    private String doGenerateToken(Map<String, Object> claims,
                                   String subject) {
        Long tokenValidityInSeconds = tokenProperties.getTokenValidityInSeconds();
        LocalDateTime validity = LocalDateTime.now().plusSeconds(tokenValidityInSeconds);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setAudience(JwtConstants.TOKEN_AUDIENCE)
                .setIssuer(JwtConstants.TOKEN_ISSUER)
                .setHeaderParam("typ", JwtConstants.TOKEN_TYPE)
                .setExpiration(FormatterUtil.convertToUtilDate(validity))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token) {
        if (Objects.isNull(token)) {
            return false;
        }
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        var expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(clock.now());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException exception) {
            log.info("Request to parse expired JWT : {} failed : {}", authToken, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.info("Request to parse unsupported JWT : {} failed : {}", authToken, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.info("Request to parse invalid JWT : {} failed : {}", authToken, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.info("Request to parse empty or null JWT : {} failed : {}", authToken, exception.getMessage());
        }
        return false;
    }
}
