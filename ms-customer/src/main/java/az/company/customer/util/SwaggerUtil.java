package az.company.customer.util;

import az.company.customer.config.properties.ApplicationProperties;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

import java.util.Collections;

/**
 * @author MehdiyevCS on 24.08.21
 */
public class SwaggerUtil {
    private SwaggerUtil() {
    }

    public static ApiInfo convertToSpringFoxApiInfo(ApplicationProperties.Swagger.SwaggerApiInfo configApiInfo) {
        ApplicationProperties.Swagger.SwaggerApiInfo.SwaggerContact configContact = configApiInfo.getContact();
        return new ApiInfo(
                configApiInfo.getTitle(),
                configApiInfo.getDescription(),
                configApiInfo.getVersion(),
                configApiInfo.getTermsOfServiceUrl(),
                new Contact(configContact.getName(), configContact.getUrl(), configContact.getEmail()),
                configApiInfo.getLicense(),
                configApiInfo.getLicenseUrl(),
                Collections.emptyList());
    }
}
