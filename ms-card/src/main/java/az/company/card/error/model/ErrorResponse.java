package az.company.card.error.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Getter
public class ErrorResponse {
    private final String id;
    private final Integer code;
    private final String message;

    public ErrorResponse(String id, Integer code, String message) {
        this.id = id;
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(String id, HttpStatus status, String message) {
        this.id = id;
        this.code = status.value();
        this.message = message;
    }
}
