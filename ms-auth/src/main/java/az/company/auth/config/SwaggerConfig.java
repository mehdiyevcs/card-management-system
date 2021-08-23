package az.company.auth.config;

import az.company.auth.config.properties.ApplicationProperties;
import az.company.auth.util.SwaggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StopWatch;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author MehdiyevCS on 24.08.21
 */
@Slf4j
@Configuration
@Import({BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig {

    private final ApplicationProperties.Swagger properties;

    public SwaggerConfig(ApplicationProperties properties) {
        this.properties = properties.getSwagger();
    }

    @Bean
    public Docket docket() {
        log.debug("Starting Swagger");
        var watch = new StopWatch();
        watch.start();

        var docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
                .paths(regex(properties.getPaths()))
                .build()
                .apiInfo(SwaggerUtil.convertToSpringFoxApiInfo(properties.getApiInfo()))
                .forCodeGeneration(true)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()));

        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "HttpAttribute.AUTHORIZATION", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        var authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }
}

