package az.company.cardorder.client.error;

import feign.error.FeignExceptionConstructor;
import feign.error.ResponseBody;
import feign.error.ResponseHeaders;
import lombok.Getter;

import java.util.Collection;
import java.util.Map;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Getter
public class MsCustomerException extends RuntimeException {
    private final MsCustomerErrorResponse errorResponse;
    private final Map<String, Collection<String>> headers;

    @FeignExceptionConstructor
    public MsCustomerException(@ResponseBody MsCustomerErrorResponse errorResponse,
                               @ResponseHeaders Map<String, Collection<String>> headers) {
        this.errorResponse = errorResponse;
        this.headers = headers;
    }
}
