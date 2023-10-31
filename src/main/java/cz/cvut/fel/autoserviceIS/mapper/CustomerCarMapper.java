package cz.cvut.fel.autoserviceIS.mapper;

import cz.cvut.fel.autoserviceIS.dto.CustomerCarDto;
import cz.cvut.fel.autoserviceIS.model.CustomerCar;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerCarMapper {

    private final ModelMapper mapper;

    public CustomerCarDto toDto(CustomerCar customerCar){
        return mapper.map(customerCar, CustomerCarDto.class);
    }

    public CustomerCar toEntity(CustomerCarDto dto) {
        return mapper.map(dto, CustomerCar.class);
    }
}
