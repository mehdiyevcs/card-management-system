package az.company.cardorder.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Slf4j
public final class ConvertUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ConvertUtil() {
    }

    public static String convertObjectToJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            log.error("{}", ex.toString());
        }
        return null;
    }

}
