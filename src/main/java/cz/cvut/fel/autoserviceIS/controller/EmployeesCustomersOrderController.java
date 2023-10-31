package cz.cvut.fel.autoserviceIS.controller;

import cz.cvut.fel.autoserviceIS.dto.EmployeesCustomersOrderDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.security.CurrentUser;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import cz.cvut.fel.autoserviceIS.service.EmployeesCustomersOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empCusOrder")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class EmployeesCustomersOrderController {

    private final EmployeesCustomersOrderService employeesCustomersOrderService;

    @GetMapping("/pool")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> getCustomerOrdersWithoutEmployee() {
        List<EmployeesCustomersOrderDto> orders = employeesCustomersOrderService.getCustomerOrdersWithoutEmployee();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customersOrders")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> getCurrentEmployeesCustomerOrders(@CurrentUser UserDetailsImpl userDetails) {
        List<EmployeesCustomersOrderDto> orders = employeesCustomersOrderService.getCurrentEmployeesCusOrders(userDetails);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> createConnectionBetweenCusOrderAndEmp(@RequestBody EmployeesCustomersOrderDto dto, @CurrentUser UserDetailsImpl userDetails) throws EntityNotFoundException {
        EmployeesCustomersOrderDto employeesCustomersOrderDto = employeesCustomersOrderService.connectEmployeeWithCustomerOrder(userDetails, dto.getOrder().getId());
        return ResponseEntity.ok(employeesCustomersOrderDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> updateConnectionBetweenCusOrderAndEmp(@RequestBody EmployeesCustomersOrderDto dto, @CurrentUser UserDetailsImpl userDetails) throws EntityNotFoundException {
        EmployeesCustomersOrderDto employeesCustomersOrderDto = employeesCustomersOrderService.updateConnectionBetweenEmployeeAndCusOrder(userDetails, dto.getId());
        return ResponseEntity.ok(employeesCustomersOrderDto);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<Void> deleteConnection(@RequestBody EmployeesCustomersOrderDto dto) throws EntityNotFoundException {
        employeesCustomersOrderService.deleteConnectionBetweenEmployeeAndCusOrder(dto.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
