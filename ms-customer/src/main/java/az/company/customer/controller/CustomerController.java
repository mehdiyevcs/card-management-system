package az.company.customer.controller;

import az.company.customer.dto.CustomerDto;
import az.company.customer.error.model.ErrorResponse;
import az.company.customer.model.CreateCustomerRequest;
import az.company.customer.service.CustomerService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad request",
                response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error",
                response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found",
                response = ErrorResponse.class),
})
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

    /**
     * Feature considered for future release
     */
    @PostMapping("/customer")
    public void createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        //need to be implemented
    }
}
