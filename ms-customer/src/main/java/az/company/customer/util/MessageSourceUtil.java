package az.company.customer.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Component
@RequiredArgsConstructor
public class MessageSourceUtil {

    private final MessageSource messageSource;

    public String getMessage(String key, Object[] arg) {
        try {
            return messageSource.getMessage(key, arg, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException ex) {
            return key;
        }
    }

    public String getMessage(String messageCode) {
        return getMessage(messageCode, null);
    }

}
