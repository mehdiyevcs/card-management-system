package az.company.auth.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author MehdiyevCS on 24.08.21
 */
@ApiModel(value = "Authentication Request", description = "Request Model for Authentication")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtAuthenticationRequest implements Serializable {

    @ApiModelProperty(value = "Customer`s Username")
    @NotEmpty(message = "Username should be provided")
    private String username;

    @ApiModelProperty(value = "Customer`s Password")
    @NotEmpty(message = "Password should be provided")
    private String password;
}
