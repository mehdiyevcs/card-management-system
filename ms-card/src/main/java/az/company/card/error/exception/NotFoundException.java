package az.company.card.error.exception;

/**
 * @author MehdiyevCS on 23.08.21
 */
public class NotFoundException extends  CommonException{
    public NotFoundException(String message) {
        super(404, message);
    }
}
