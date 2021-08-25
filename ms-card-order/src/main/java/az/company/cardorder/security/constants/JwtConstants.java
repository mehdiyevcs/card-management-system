package az.company.cardorder.security.constants;

/**
 * @author MehdiyevCS on 24.08.21
 */
public final class JwtConstants {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "Company";
    public static final String TOKEN_AUDIENCE = "api-clients";

    private JwtConstants() {
    }
}
