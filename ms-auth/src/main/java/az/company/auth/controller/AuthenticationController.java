package az.company.auth.controller;

import az.company.auth.error.model.ErrorResponse;
import az.company.auth.model.JwtAuthenticationRequest;
import az.company.auth.model.JwtAuthenticationResponse;
import az.company.auth.service.AuthenticationService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request",
                    response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error",
                    response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized",
                    response = ErrorResponse.class),
            @ApiResponse(code = 423, message = "User Locked or disabled",
                    response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden",
                    response = ErrorResponse.class)
    })
    @PostMapping("/login")
    public JwtAuthenticationResponse login(@Valid @RequestBody JwtAuthenticationRequest request) {
        return authenticationService.login(request);
    }

}
