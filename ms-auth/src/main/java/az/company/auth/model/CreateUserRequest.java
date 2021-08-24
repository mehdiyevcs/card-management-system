package az.company.auth.model;

import az.company.auth.domain.enumeration.UserState;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author MehdiyevCS on 24.08.21
 */
@Data
@Builder
public class CreateUserRequest {

    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private UserState status;

    @NotNull
    private String role;

}
