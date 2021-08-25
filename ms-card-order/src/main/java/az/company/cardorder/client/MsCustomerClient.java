package az.company.cardorder.client;

import az.company.cardorder.client.error.MsCustomerException;
import az.company.cardorder.client.model.CustomerDto;
import az.company.cardorder.config.MsCustomerFeignConfig;
import feign.error.ErrorHandling;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * @author MehdiyevCS on 25.08.21
 */
@FeignClient(name = "msCustomerClient", url = "${application.client.ms-customer.url}",
        configuration = MsCustomerFeignConfig.class)
public interface MsCustomerClient {

    @ErrorHandling(defaultException = MsCustomerException.class)
    @GetMapping("/api/customer")
    public Optional<CustomerDto> getCustomer(@RequestParam(required = false) Long id,
                                             @RequestParam(required = false) String pin);

}
