package az.company.cardorder.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author MammadovMB on 2/14/2021
 */

public final class FormatterUtil {

    private FormatterUtil() {
    }

    public static Date convertToUtilDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
