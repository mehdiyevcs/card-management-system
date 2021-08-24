package az.company.cardorder.error.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Getter
public class InvalidInputException extends RuntimeException {

    private String message;
    private List<Object> messageArguments;

    public InvalidInputException(String message, List<Object> messageArguments) {
        this.message = message;
        this.messageArguments = messageArguments;
    }

    public static InvalidInputException of(String message, List<Object> messageArguments) {
        return new InvalidInputException(message, messageArguments);
    }

    public static InvalidInputException of(String message) {
        return new InvalidInputException(message, Collections.emptyList());
    }

    public Object[] messageArguments() {
        return messageArguments != null ? messageArguments.toArray() : new Object[0];
    }

    public InvalidInputException msgArgument(Object arg) {
        if (messageArguments == null) {
            messageArguments = new ArrayList<>();
        }
        messageArguments.add(arg);
        return this;
    }

    public InvalidInputException msgArgument(Object... args) {
        if (messageArguments == null) {
            messageArguments = new ArrayList<>(args.length);
        }
        messageArguments.addAll(List.of(args));
        return this;
    }

}
