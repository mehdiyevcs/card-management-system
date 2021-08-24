package az.company.auth.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author MehdiyevCS on 24.08.21
 */
@ApiModel(value = "Authentication Response", description = "Response Model for Authentication")
@Getter
public class JwtAuthenticationResponse implements Serializable {

    @ApiModelProperty(value = "Token", example = "xxxx.yyyy.zzzz")
    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }
}
