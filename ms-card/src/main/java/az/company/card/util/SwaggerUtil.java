package az.company.card.util;

import az.company.card.config.properties.ApplicationProperties;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

import java.util.Collections;

public final class SwaggerUtil {

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
