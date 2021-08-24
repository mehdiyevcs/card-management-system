package az.company.customer.mapper;

import az.company.customer.domain.Customer;
import az.company.customer.dto.CustomerDto;
import org.mapstruct.Mapper;

/**
 * @author MehdiyevCS on 24.08.21
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerMapper extends EntityMapper<CustomerDto, Customer> {
    default Customer fromId(Long id) {
        if (id == null)
            return null;

        var customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
