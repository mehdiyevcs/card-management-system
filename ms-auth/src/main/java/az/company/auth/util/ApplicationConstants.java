package az.company.auth.util;

/**
 * @author MehdiyevCS on 24.08.21
 */
public interface ApplicationConstants {
    interface Attribute {
        String REQUEST_ID = "request_id";
        String ELAPSED_TIME = "elapsed_time";
        String X_FORWARDED_FOR = "X-Forwarded-For";
        String AUTHORIZATION = "Authorization";
    }
}
