package az.company.customer.service;

import az.company.customer.dto.CustomerDto;
import az.company.customer.mapper.CustomerMapper;
import az.company.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        throw new RuntimeException("Parameters are empty");
    }

    @Transactional(readOnly = true)
    public Page<CustomerDto> findAll(Pageable pageable) {
        return customerRepository
                .findAll(pageable)
                .map(customerMapper::toDto);
    }

    public CustomerDto save(CustomerDto customerDto) {
        if (getCustomer(null, customerDto.getPin()).isPresent())
            throw new RuntimeException("Customer with Pin already exists");

        var customer = customerMapper.toEntity(customerDto);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }
}
