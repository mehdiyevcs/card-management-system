package az.company.auth.error.exception;

/**
 * @author MehdiyevCS on 25.08.21
 */
public class AuthException extends CommonException {
    public AuthException(String message) {
        super(401, message);
    }
}
