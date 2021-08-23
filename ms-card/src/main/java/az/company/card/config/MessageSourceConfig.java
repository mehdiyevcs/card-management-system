package az.company.card.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Configuration
public class MessageSourceConfig {

    private static final Locale LOCALE_AZ = new Locale("az");
    private static final Locale LOCALE_EN = new Locale("en");

    @Bean
    public MessageSource messageSource() {
        var rs = new ResourceBundleMessageSource();
        rs.setBasename("i18n/messages");
        rs.setDefaultEncoding("UTF-8");
        return rs;
    }

    @Bean
    public LocaleResolver localeResolver() {
        var localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(LOCALE_EN);
        localeResolver.setSupportedLocales(Arrays.asList(LOCALE_AZ, LOCALE_EN));
        return localeResolver;
    }
}