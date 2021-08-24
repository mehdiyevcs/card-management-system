package az.company.card.error.exception;

import lombok.Getter;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Getter
public class CommonException extends RuntimeException {
    private final Integer code;
    private final String message;

    public CommonException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
