package az.company.cardorder.security.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Slf4j
public class TokenUtil {

    public static Optional<String> getUsernameFromContextHolder() {
        Authentication authResult = SecurityContextHolder.getContext().getAuthentication();

        if (authResult instanceof UsernamePasswordAuthenticationToken) {
            var authToken = (UsernamePasswordAuthenticationToken) authResult;
            String username = (String) authToken.getPrincipal();
            log.info("Username obtained from SecContextHolder : {}", username);
            return Optional.of(username);
        }
        return Optional.empty();
    }
}
