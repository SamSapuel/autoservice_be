package cz.cvut.fel.autoserviceIS.controller;

import cz.cvut.fel.autoserviceIS.dto.EmployeeOrderDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.security.CurrentUser;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import cz.cvut.fel.autoserviceIS.service.EmployeeOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employeeOrder")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class EmployeeOrderController {

    private final EmployeeOrderService employeeOrderService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> getEmployeesOrders(@CurrentUser UserDetailsImpl userDetails) {
        List<EmployeeOrderDto> orders = employeeOrderService.getCurrentEmployeeOrders(userDetails);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> createOrder(@RequestBody EmployeeOrderDto dto, @CurrentUser UserDetailsImpl userDetails) throws EntityNotFoundException {
        EmployeeOrderDto employeeOrderDto = employeeOrderService.createOrder(dto, userDetails);
        return ResponseEntity.ok(employeeOrderDto);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<Void> deleteOrder(@RequestBody EmployeeOrderDto dto) throws EntityNotFoundException {
        employeeOrderService.deleteOrder(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
