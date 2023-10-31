package cz.cvut.fel.autoserviceIS.mapper;

import cz.cvut.fel.autoserviceIS.dto.CustomerOrderItemDto;
import cz.cvut.fel.autoserviceIS.model.CustomerOrderItem;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerOrderItemMapper {

    private final ModelMapper mapper;

    public CustomerOrderItemDto toDto(CustomerOrderItem customerOrderItem) {
        return mapper.map(customerOrderItem, CustomerOrderItemDto.class);
    }

    public CustomerOrderItem toEntity(CustomerOrderItemDto dto) {
        return  mapper.map(dto, CustomerOrderItem.class);
    }
}
