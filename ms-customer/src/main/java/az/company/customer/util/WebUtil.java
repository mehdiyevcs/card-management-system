package az.company.customer.util;

import az.company.customer.constant.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Slf4j
@Component
public class WebUtil {

    @Autowired
    private HttpServletRequest httpServletRequest;

    private ContentCachingRequestWrapper requestWrapper;

    public void initializeRequestWrapper(ContentCachingRequestWrapper requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    public String getRequestId() {
        return Optional.ofNullable(httpServletRequest)
                .map(req -> String.valueOf(req.getAttribute(ApplicationConstants.HttpAttribute.REQUEST_ID)))
                .orElse(null);
    }

    public String getRequestUri() {
        return Optional.ofNullable(httpServletRequest)
                .map(req -> req.getMethod() + " " + req.getRequestURI())
                .orElse("");
    }

    public Long getElapsedTime() {
        return Optional.ofNullable(httpServletRequest)
                .map(req -> String.valueOf(req.getAttribute(ApplicationConstants.HttpAttribute.ELAPSED_TIME)))
                .filter(StringUtils::isNumeric)
                .map(t -> System.currentTimeMillis() - Long.parseLong(t))
                .orElse(-1L);
    }

    public String getBearerTokenFromAuthorizationHeader() {
        return Optional
                .ofNullable(httpServletRequest.getHeader(ApplicationConstants.HttpAttribute.AUTHORIZATION))
                .filter(token -> token.startsWith(ApplicationConstants.HttpAttribute.BEARER))
                .orElse(null);
    }

    public void createHelperHttpAttributesAndPutToMdc(HttpServletRequest httpServletRequest) {
        final String requestId = CommonUtil.generateId();
        httpServletRequest.setAttribute(ApplicationConstants.HttpAttribute.REQUEST_ID, requestId);
        httpServletRequest.setAttribute(ApplicationConstants.HttpAttribute.ELAPSED_TIME, System.currentTimeMillis());
        putMdcField(ApplicationConstants.HttpAttribute.REQUEST_ID, requestId);
    }

    public void putMdcField(String key, String value) {
        MDC.put(key, value);
    }

    public String getRequestBody() {
        try {
            return Optional.ofNullable(requestWrapper)
                    .map(ContentCachingRequestWrapper::getContentAsByteArray)
                    .map(String::new)
                    .orElse("[null]");
        } catch (Exception e) {
            log.error("{} error occurs while getting request body for uri {}", e.getMessage(), getRequestUri(), e);
            return "[failed]";
        }
    }

}
