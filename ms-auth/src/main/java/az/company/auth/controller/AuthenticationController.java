package az.company.auth.controller;

import az.company.auth.model.JwtAuthenticationRequest;
import az.company.auth.model.JwtAuthenticationResponse;
import az.company.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author MehdiyevCS on 23.08.21
 */
@RestController
@RequestMapping("/api/auth")
@Validated
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@Valid @RequestBody JwtAuthenticationRequest request) {
        return authenticationService.login(request);
    }

}
