package cz.cvut.fel.autoserviceIS.controller;

import cz.cvut.fel.autoserviceIS.dto.CustomerOrderItemDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.service.CustomerOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customerOrderItem")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class CustomerOrderItemController {

    private final CustomerOrderItemService customerOrderItemService;

    @PostMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> createCustomerOrderItem(@PathVariable Long orderId, @RequestBody List<CustomerOrderItemDto> dtos) throws EntityNotFoundException {
        List<CustomerOrderItemDto> dto = customerOrderItemService.createCustomerOrderItem(orderId, dtos);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> updateCustomerOrderItemDescOrPrice(@PathVariable Long orderId, @RequestBody CustomerOrderItemDto dto) throws EntityNotFoundException {
        CustomerOrderItemDto customerOrderItemDto = customerOrderItemService.updateCustomerOrderItemDescriptionOrTotalPrice(orderId, dto);
        return ResponseEntity.ok(customerOrderItemDto);
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<Void> deleteCustomerOrderItem(@PathVariable Long orderId, @RequestBody CustomerOrderItemDto dto) throws EntityNotFoundException {
        customerOrderItemService.deleteCustomerOrderItem(orderId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
