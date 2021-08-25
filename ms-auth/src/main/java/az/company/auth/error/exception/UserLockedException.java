package az.company.auth.error.exception;

/**
 * @author MehdiyevCS on 25.08.21
 */
public class UserLockedException extends CommonException {
    public UserLockedException(String message) {
        super(423, message);
    }
}
