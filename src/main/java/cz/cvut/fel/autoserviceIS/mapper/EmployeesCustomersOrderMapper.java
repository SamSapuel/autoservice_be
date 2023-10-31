package cz.cvut.fel.autoserviceIS.mapper;

import cz.cvut.fel.autoserviceIS.dto.EmployeesCustomersOrderDto;
import cz.cvut.fel.autoserviceIS.model.EmployeesCustomersOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeesCustomersOrderMapper {

    private final ModelMapper mapper;

    public EmployeesCustomersOrderDto toDto(EmployeesCustomersOrder entity) {
        return mapper.map(entity, EmployeesCustomersOrderDto.class);
    }

    public EmployeesCustomersOrder toEntity(EmployeesCustomersOrderDto dto) {
        return mapper.map(dto, EmployeesCustomersOrder.class);
    }
}
