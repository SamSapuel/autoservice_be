package cz.cvut.fel.autoserviceIS.mapper;

import cz.cvut.fel.autoserviceIS.dto.ItemDto;
import cz.cvut.fel.autoserviceIS.model.Item;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemMapper {

    private final ModelMapper mapper;

    public ItemDto toDto(Item entity) {
        return mapper.map(entity, ItemDto.class);
    }

    public Item toEntity(ItemDto dto) {
        return mapper.map(dto, Item.class);
    }
}
