package az.company.auth.filter;

import az.company.auth.security.TokenCreator;
import az.company.auth.security.constants.JwtConstants;
import az.company.auth.constant.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author MehdiyevCS on 24.08.21
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final TokenCreator tokenCreator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String clientIP = Optional
                .ofNullable(request.getHeader(ApplicationConstants.HttpAttribute.X_FORWARDED_FOR))
                .orElse(request.getRemoteAddr());
        String requestURI = Optional.ofNullable(request)
                .map(req -> req.getMethod() + " " + req.getRequestURI())
                .orElse("");

        log.info("ClientIP : {}  Request: {}", clientIP, requestURI);
        log.info("JwtAuthorizationFilter is started");
        var authResult = getAuthentication(request);
        if (Objects.isNull(authResult)) {
            log.info("JwtAuthorizationFilter failed");
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authResult);
        log.info("JwtAuthorizationFilter success");
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String jwtToken = extractJwtFromHeader(request);

        if (Objects.nonNull(jwtToken) && tokenCreator.validateJwtToken(jwtToken)) {
            var claims = tokenCreator.getAllClaimsFromToken(jwtToken);
            String username = claims.getSubject();
            log.info("Username extracted from token: {}: ", username);

            var roles = ((List<String>) claims.get("roles")).stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            var allowedClients = (List<Long>) claims.get("allowedClients");
            log.info("Allowed extracted from token: {}: ", allowedClients);

            return new UsernamePasswordAuthenticationToken(username, allowedClients, roles);
        }
        return null;
    }

    private String extractJwtFromHeader(HttpServletRequest request) {
        String authHeaderVal = request.getHeader(JwtConstants.TOKEN_HEADER);

        if (authHeaderVal != null && authHeaderVal.startsWith(JwtConstants.TOKEN_PREFIX)) {
            return authHeaderVal.replace(JwtConstants.TOKEN_PREFIX, "");
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();
        var uriList = new String[]{"/v2/api/docs",
                "/swagger", "/actuator", "/csrf",
                "/webjars/", "/configuration"};

        return Arrays.stream(uriList)
                .anyMatch(uri::startsWith);
    }
}