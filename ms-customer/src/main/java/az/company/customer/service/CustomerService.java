package az.company.customer.service;

import az.company.customer.dto.CustomerDto;
import az.company.customer.error.exception.InvalidInputException;
import az.company.customer.error.validation.ValidationMessage;
import az.company.customer.mapper.CustomerMapper;
import az.company.customer.model.CreateCustomerRequest;
import az.company.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;


/**
 * @author MehdiyevCS on 24.08.21
 */
@Service
@Transactional
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    public Optional<CustomerDto> getCustomer(Long id, String pin) {
        if (Objects.nonNull(id))
            return customerRepository.findById(id).map(customerMapper::toDto);
        if (Objects.nonNull(pin))
            return customerRepository.findByPin(pin).map(customerMapper::toDto);
        throw InvalidInputException.of(ValidationMessage.CUSTOMER_MISSING_PARAMETER);
    }

    @Transactional(readOnly = true)
    public Page<CustomerDto> findAll(Pageable pageable) {
        return customerRepository
                .findAll(pageable)
                .map(customerMapper::toDto);
    }

    public CustomerDto save(CreateCustomerRequest createCustomerRequest) {
        if (getCustomer(null, createCustomerRequest.getPin()).isPresent())
            throw InvalidInputException.of(ValidationMessage.CUSTOMER_DUPLICATE,
                    Collections.singletonList(createCustomerRequest.getPin()));

        var customerDto = CustomerDto.builder()
                .pin(createCustomerRequest.getPin())
                .fullName(createCustomerRequest.getFullName())
                .build();

        var customer = customerMapper.toEntity(customerDto);
        customer.setUsername("anon");//In future take from securityContextHolder
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }
}
