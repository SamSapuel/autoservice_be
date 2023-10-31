package cz.cvut.fel.autoserviceIS.controller;

import cz.cvut.fel.autoserviceIS.dto.ItemDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> findById(@PathVariable Long id) throws EntityNotFoundException {
        ItemDto itemDto = itemService.findById(id);
        return ResponseEntity.ok(itemDto);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> getAllItems() {
        List<ItemDto> items = itemService.findAll();
        return ResponseEntity.ok(items);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> createItem(@RequestBody ItemDto dto) {
        ItemDto itemDto = itemService.createItem(dto);
        return ResponseEntity.ok(itemDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> updateStatusOfItem(@RequestBody ItemDto dto) throws EntityNotFoundException {
        ItemDto itemDto = itemService.updateStatusOfItem(dto);
        return ResponseEntity.ok(itemDto);
    }
}
