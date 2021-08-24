package az.company.customer;

import az.company.customer.config.properties.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableSwagger2
@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class MsCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCustomerApplication.class, args);
    }

}
