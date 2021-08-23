package az.company.card.util;

import az.company.card.constant.CommonConstants;
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
                .map(req -> String.valueOf(req.getAttribute(CommonConstants.HttpAttribute.REQUEST_ID)))
                .orElse(null);
    }

    public String getRequestUri() {
        return Optional.ofNullable(httpServletRequest)
                .map(req -> req.getMethod() + " " + req.getRequestURI())
                .orElse("");
    }

    public Long getElapsedTime() {
        return Optional.ofNullable(httpServletRequest)
                .map(req -> String.valueOf(req.getAttribute(CommonConstants.HttpAttribute.ELAPSED_TIME)))
                .filter(StringUtils::isNumeric)
                .map(t -> System.currentTimeMillis() - Long.parseLong(t))
                .orElse(-1L);
    }

    public String getBearerTokenFromAuthorizationHeader() {
        return Optional
                .ofNullable(httpServletRequest.getHeader(CommonConstants.HttpAttribute.AUTHORIZATION))
                .filter(token -> token.startsWith(CommonConstants.HttpAttribute.BEARER))
                .orElse(null);
    }

    public void createHelperHttpAttributesAndPutToMdc(HttpServletRequest httpServletRequest) {
        final String requestId = CommonUtil.generateId();
        httpServletRequest.setAttribute(CommonConstants.HttpAttribute.REQUEST_ID, requestId);
        httpServletRequest.setAttribute(CommonConstants.HttpAttribute.ELAPSED_TIME, System.currentTimeMillis());
        putMdcField(CommonConstants.HttpAttribute.REQUEST_ID, requestId);
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
