package az.company.auth.service;

import az.company.auth.error.exception.AuthException;
import az.company.auth.error.exception.NotFoundException;
import az.company.auth.error.exception.UserLockedException;
import az.company.auth.error.validation.ValidationMessage;
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

        var user = repository.findByUsername(jwtAuthenticationRequest.getUsername())
                .orElseThrow(() -> new NotFoundException(ValidationMessage.USER_NOT_FOUND));

        String token = tokenCreator.generateToken(jwtAuthenticationRequest.getUsername(),
                Arrays.asList(user.getRole().getName()));
        return new JwtAuthenticationResponse(token);
    }

    public void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(ValidationMessage.USER_BAD_CREDENTIALS);
        } catch (DisabledException | LockedException e) {
            log.info("Error type is: {} , message is {}", e.getClass(), e.getMessage());
            throw new UserLockedException(ValidationMessage.USER_LOCKED);
        } catch (UsernameNotFoundException e) {
            throw new NotFoundException(ValidationMessage.USER_NOT_FOUND);
        } catch (AuthenticationException e) {
            log.info("Error type is: {} , Error message is", e.getClass(), e.getMessage());
            throw new AuthException(ValidationMessage.USER_NOT_AUTHENTICATED);
        }
    }

    public String validateToken(String token) {
        return tokenCreator.isTokenValid(token) ? "VALID TOKEN" : "INVALID TOKEN";
    }
}