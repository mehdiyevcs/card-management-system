package az.company.auth.constant;

/**
 * @author MehdiyevCS on 24.08.21
 */
public interface ApplicationConstants {
    interface HttpAttribute {
        String REQUEST_ID = "request_id";
        String ELAPSED_TIME = "elapsed_time";
        String X_FORWARDED_FOR = "X-Forwarded-For";
        String AUTHORIZATION = "Authorization";
        String BEARER = "Bearer";
    }
}
