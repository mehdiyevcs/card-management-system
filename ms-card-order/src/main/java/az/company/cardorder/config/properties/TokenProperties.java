package az.company.cardorder.config.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author MehdiyevCS on 24.08.21
 */
@ConfigurationProperties(prefix = "application.security.authentication.jwt")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenProperties {

    private String base64Secret;
    private Long tokenValidityInSeconds;

}
