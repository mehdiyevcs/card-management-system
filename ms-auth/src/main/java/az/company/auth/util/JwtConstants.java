package az.company.auth.util;

/**
 * @author MehdiyevCS on 24.08.21
 */
public final class JwtConstants {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "Company";
    public static final String TOKEN_AUDIENCE = "api-clients";
    public static final long TOKEN_EXPIRATION = 864000000L;
    public static final String JWT_SECRET = "QjJCX1BBWU1FTlRfU09MVVRJT05THDSGIGDSUGAISdHOo123dSD" +
            "SDADIOSUDSIUGFVSDSDsWC2sda41SADSDVDASDV";

    private JwtConstants() {
    }
}
