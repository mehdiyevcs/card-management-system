package az.company.customer.controller;

import az.company.customer.dto.CustomerDto;
import az.company.customer.model.CreateCustomerRequest;
import az.company.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author MehdiyevCS on 24.08.21
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomers(Pageable pageable) {
        Page<CustomerDto> customerDtoPage = customerService.findAll(pageable);
        return customerDtoPage.getContent();
    }

    @GetMapping("/customer")
    public Optional<CustomerDto> getCustomer(@RequestParam(required = false) Long id,
                                             @RequestParam(required = false) String pin) {
        return customerService.getCustomer(id, pin);
    }

    @PostMapping("/customer")
    public CustomerDto createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        return customerService.save(createCustomerRequest);
    }

}