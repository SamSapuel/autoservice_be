package cz.cvut.fel.autoserviceIS.mapper;

import cz.cvut.fel.autoserviceIS.dto.EmployeeOrderItemDto;
import cz.cvut.fel.autoserviceIS.model.EmployeeOrderItem;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeOrderItemMapper {

    private final ModelMapper mapper;

    public EmployeeOrderItemDto toDto(EmployeeOrderItem entity) {
        return mapper.map(entity, EmployeeOrderItemDto.class);
    }

    public EmployeeOrderItem toEntity(EmployeeOrderItemDto dto) {
        return mapper.map(dto, EmployeeOrderItem.class);
    }
}
