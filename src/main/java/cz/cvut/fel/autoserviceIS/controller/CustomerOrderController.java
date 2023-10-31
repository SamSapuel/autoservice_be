package cz.cvut.fel.autoserviceIS.controller;

import cz.cvut.fel.autoserviceIS.dto.CustomerOrderDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.security.CurrentUser;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import cz.cvut.fel.autoserviceIS.service.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customerOrder")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> getOrderById(@PathVariable long id) throws EntityNotFoundException {
        CustomerOrderDto customerOrderDto = customerOrderService.findById(id);
        return ResponseEntity.ok(customerOrderDto);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> getCustomersOrders(@CurrentUser UserDetailsImpl userDetails) {
        List<CustomerOrderDto> orders = customerOrderService.getCurrentCustomersOrders(userDetails);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS','ADMIN_ACCESS')")
    public ResponseEntity<?> saveOrder(@RequestBody CustomerOrderDto dto, @CurrentUser UserDetailsImpl userDetails) throws EntityNotFoundException {
        CustomerOrderDto customerOrderDto = customerOrderService.createOrder(dto, userDetails);
        return ResponseEntity.ok(customerOrderDto);
    }

    @PutMapping("/updateStatus")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS','CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> updateStatusOfOrder(@RequestBody CustomerOrderDto dto) throws EntityNotFoundException {
        CustomerOrderDto customerOrderDto = customerOrderService.updateStatusOfOrder(dto);
        return ResponseEntity.ok(customerOrderDto);
    }

    @PutMapping("/updateTime")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS','CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> updateTimeOfOrder(@RequestBody CustomerOrderDto dto) throws EntityNotFoundException {
        CustomerOrderDto customerOrderDto = customerOrderService.updateTimeOfOrder(dto);
        return ResponseEntity.ok(customerOrderDto);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<Void> deleteOrder(@RequestBody CustomerOrderDto dto) throws EntityNotFoundException {
        customerOrderService.deleteOrder(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
