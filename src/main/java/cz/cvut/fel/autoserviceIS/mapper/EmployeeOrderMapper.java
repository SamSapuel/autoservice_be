package cz.cvut.fel.autoserviceIS.mapper;

import cz.cvut.fel.autoserviceIS.dto.EmployeeOrderDto;
import cz.cvut.fel.autoserviceIS.model.EmployeeOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeOrderMapper {

    private final ModelMapper mapper;

    public EmployeeOrderDto toDto(EmployeeOrder order) {
        return mapper.map(order, EmployeeOrderDto.class);
    }

    public EmployeeOrder toEntity(EmployeeOrderDto dto) {
        return mapper.map(dto, EmployeeOrder.class);
    }
}
