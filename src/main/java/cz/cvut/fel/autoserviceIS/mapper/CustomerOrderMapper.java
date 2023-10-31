package cz.cvut.fel.autoserviceIS.mapper;

import cz.cvut.fel.autoserviceIS.dto.CustomerOrderDto;
import cz.cvut.fel.autoserviceIS.model.CustomerOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerOrderMapper {

    private final ModelMapper mapper;

    public CustomerOrderDto toDto(CustomerOrder customerOrder) {
        return mapper.map(customerOrder, CustomerOrderDto.class);
    }

    public CustomerOrder toEntity(CustomerOrderDto dto) {
        return mapper.map(dto, CustomerOrder.class);
    }
}
