package cz.cvut.fel.autoserviceIS.service;

import cz.cvut.fel.autoserviceIS.dto.ItemDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.ItemMapper;
import cz.cvut.fel.autoserviceIS.model.Item;
import cz.cvut.fel.autoserviceIS.repository.ItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemsRepository itemsRepository;
    private final ItemMapper itemMapper;

    @Transactional
    public ItemDto findById(Long id) throws EntityNotFoundException {
        Item item = itemsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item with id:"  + id + " not found"));
        return itemMapper.toDto(item);
    }

    @Transactional
    public List<ItemDto> findAll() {
        List<Item> items = itemsRepository.findAll();
        return items.stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemDto createItem(ItemDto dto) {
        Item item = itemMapper.toEntity(dto);
        Item res = itemsRepository.save(item);
        return itemMapper.toDto(res);
    }

    @Transactional
    public ItemDto updateStatusOfItem(ItemDto dto) throws EntityNotFoundException {
        Item item = itemsRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Item with id: " + dto.getId() + " not found"));
        item.setInStock(dto.isInStock());
        Item res = itemsRepository.save(item);
        return itemMapper.toDto(res);
    }
}
