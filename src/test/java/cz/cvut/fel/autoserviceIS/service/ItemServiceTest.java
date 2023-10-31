package cz.cvut.fel.autoserviceIS.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.cvut.fel.autoserviceIS.dto.ItemDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.ItemMapper;
import cz.cvut.fel.autoserviceIS.model.Item;
import cz.cvut.fel.autoserviceIS.repository.ItemsRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ItemService.class})
@ExtendWith(SpringExtension.class)
class ItemServiceTest {
    @MockBean
    private ItemMapper itemMapper;

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemsRepository itemsRepository;

    /**
     * Method under test: {@link ItemService#findById(Long)}
     */
    @Test
    void testFindById() throws EntityNotFoundException {
        Item item = new Item();
        item.setId(1L);
        item.setInStock(true);
        item.setName("Name");
        item.setPrice(1);
        Optional<Item> ofResult = Optional.of(item);
        when(itemsRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setInStock(true);
        itemDto.setName("Name");
        itemDto.setPrice(1);
        when(itemMapper.toDto(Mockito.<Item>any())).thenReturn(itemDto);
        assertSame(itemDto, itemService.findById(1L));
        verify(itemsRepository).findById(Mockito.<Long>any());
        verify(itemMapper).toDto(Mockito.<Item>any());
    }

    @Test
    void testFindAll() {
        when(itemsRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(itemService.findAll().isEmpty());
        verify(itemsRepository).findAll();
    }
    @Test
    void testCreateItem() {
        Item item = new Item();
        item.setId(1L);
        item.setInStock(true);
        item.setName("Name");
        item.setPrice(1);
        when(itemsRepository.save(Mockito.<Item>any())).thenReturn(item);

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setInStock(true);
        itemDto.setName("Name");
        itemDto.setPrice(1);

        Item item2 = new Item();
        item2.setId(1L);
        item2.setInStock(true);
        item2.setName("Name");
        item2.setPrice(1);
        when(itemMapper.toDto(Mockito.<Item>any())).thenReturn(itemDto);
        when(itemMapper.toEntity(Mockito.<ItemDto>any())).thenReturn(item2);

        ItemDto dto = new ItemDto();
        dto.setId(1L);
        dto.setInStock(true);
        dto.setName("Name");
        dto.setPrice(1);
        assertSame(itemDto, itemService.createItem(dto));
        verify(itemsRepository).save(Mockito.<Item>any());
        verify(itemMapper).toDto(Mockito.<Item>any());
        verify(itemMapper).toEntity(Mockito.<ItemDto>any());
    }

    @Test
    void testUpdateStatusOfItem() throws EntityNotFoundException {
        Item item = new Item();
        item.setId(1L);
        item.setInStock(true);
        item.setName("Name");
        item.setPrice(1);
        Optional<Item> ofResult = Optional.of(item);

        Item item2 = new Item();
        item2.setId(1L);
        item2.setInStock(true);
        item2.setName("Name");
        item2.setPrice(1);
        when(itemsRepository.save(Mockito.<Item>any())).thenReturn(item2);
        when(itemsRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setInStock(true);
        itemDto.setName("Name");
        itemDto.setPrice(1);
        when(itemMapper.toDto(Mockito.<Item>any())).thenReturn(itemDto);

        ItemDto dto = new ItemDto();
        dto.setId(1L);
        dto.setInStock(true);
        dto.setName("Name");
        dto.setPrice(1);
        assertSame(itemDto, itemService.updateStatusOfItem(dto));
        verify(itemsRepository).save(Mockito.<Item>any());
        verify(itemsRepository).findById(Mockito.<Long>any());
        verify(itemMapper).toDto(Mockito.<Item>any());
    }

    @Test
    void testUpdateStatusOfItem2() throws EntityNotFoundException {
        Item item = new Item();
        item.setId(1L);
        item.setInStock(true);
        item.setName("Name");
        item.setPrice(1);
        when(itemsRepository.save(Mockito.<Item>any())).thenReturn(item);
        when(itemsRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setInStock(true);
        itemDto.setName("Name");
        itemDto.setPrice(1);
        when(itemMapper.toDto(Mockito.<Item>any())).thenReturn(itemDto);

        ItemDto dto = new ItemDto();
        dto.setId(1L);
        dto.setInStock(true);
        dto.setName("Name");
        dto.setPrice(1);
        assertThrows(EntityNotFoundException.class, () -> itemService.updateStatusOfItem(dto));
        verify(itemsRepository).findById(Mockito.<Long>any());
    }
}

