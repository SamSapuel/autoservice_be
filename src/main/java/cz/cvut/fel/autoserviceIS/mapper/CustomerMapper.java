package cz.cvut.fel.autoserviceIS.mapper;

import cz.cvut.fel.autoserviceIS.dto.CustomerDto;
import cz.cvut.fel.autoserviceIS.model.Customer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

    private final ModelMapper mapper;

    public CustomerDto toDto(Customer customer) {
        return mapper.map(customer, CustomerDto.class);
    }

    public Customer toEntity(CustomerDto dto) {
        return mapper.map(dto, Customer.class);
    }
}
