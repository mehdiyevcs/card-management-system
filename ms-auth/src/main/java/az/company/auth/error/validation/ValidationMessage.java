package az.company.auth.error.validation;

/**
 * @author MehdiyevCS on 23.08.21
 */
public interface ValidationMessage {
    String USER_NOT_FOUND = "user.notFound.message";
    String USER_BAD_CREDENTIALS = "user.badCredentials.message";
    String USER_LOCKED = "user.locked.message";
    String USER_NOT_AUTHENTICATED = "user.notAuth.message";
}
