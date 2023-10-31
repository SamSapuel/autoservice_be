package cz.cvut.fel.autoserviceIS.mapper;

import cz.cvut.fel.autoserviceIS.dto.EmployeeDto;
import cz.cvut.fel.autoserviceIS.model.Employee;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final ModelMapper mapper;

    public EmployeeDto toDto(Employee employee) {
        return mapper.map(employee, EmployeeDto.class);
    }

    public Employee toEntity(EmployeeDto dto) {
        return mapper.map(dto, Employee.class);
    }
}
