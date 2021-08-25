package az.company.cardorder.config;

import az.company.cardorder.client.MsCustomerClient;
import feign.codec.ErrorDecoder;
import feign.error.AnnotationErrorDecoder;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Configuration
public class MsCustomerFeignConfig {
    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return AnnotationErrorDecoder.builderFor(MsCustomerClient.class)
                .withResponseBodyDecoder(new JacksonDecoder()).build();
    }
}
