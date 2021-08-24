package az.company.auth.service;

import az.company.auth.model.JwtAuthenticationRequest;
import az.company.auth.model.JwtAuthenticationResponse;
import az.company.auth.repository.UserRepository;
import az.company.auth.security.TokenCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final TokenCreator tokenCreator;

    public JwtAuthenticationResponse login(JwtAuthenticationRequest jwtAuthenticationRequest) {
        authenticate(jwtAuthenticationRequest.getUsername(), jwtAuthenticationRequest.getPassword());

        var user = repository.findByUsername(jwtAuthenticationRequest.getUsername()).get();

        String token = tokenCreator.generateToken(jwtAuthenticationRequest.getUsername(),
                Arrays.asList(user.getRole().getName()), user.getId());
        return new JwtAuthenticationResponse(token);
    }

    public void authenticate(String username, String password) {
        Authentication authResult = null;
        try {
            authResult = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (BadCredentialsException e) {
            //throw ServiceException.of(ErrorCode.USERNAME_OR_PASSWORD_INCORRECT, List.of());
        } catch (DisabledException | LockedException e) {
            log.info("Error type is: {} , message is {}", e.getClass(), e.getMessage());
            //throw ServiceException.of(ErrorCode.USER_LOCKED, List.of(username));
        } catch (UsernameNotFoundException e) {
            //throw ServiceException.of(ErrorCode.USER_NOT_FOUND, List.of(username));
        } catch (AuthenticationException e) {
            log.info("Error type is: {} , Error message is", e.getClass(), e.getMessage());
            //throw ServiceException.of(ErrorCode.USER_NOT_AUTHENTICATED, List.of(username));
        }
    }

    public String validateToken(String token) {
        return tokenCreator.isTokenValid(token) ? "VALID TOKEN" : "INVALID TOKEN";
    }
}