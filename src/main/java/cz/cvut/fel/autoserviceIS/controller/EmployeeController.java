package cz.cvut.fel.autoserviceIS.controller;

import cz.cvut.fel.autoserviceIS.dto.EmployeeDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.model.Employee;
import cz.cvut.fel.autoserviceIS.security.CurrentUser;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import cz.cvut.fel.autoserviceIS.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_ACCESS')")
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/currentEmployee")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> getCurrentUser(@CurrentUser UserDetailsImpl userDetails) {
        Employee employee = (Employee) userDetails.getUser();
        return ResponseEntity.ok(employee);
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ACCESS', 'EMPLOYEE_ACCESS')")
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDto dto) {
        EmployeeDto employeeDto = employeeService.addEmployee(dto);
        return ResponseEntity.ok(employeeDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDto dto) throws EntityNotFoundException {
        EmployeeDto employeeDto = employeeService.updateEmployee(dto);
        return ResponseEntity.ok(employeeDto);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ACCESS')")
    public ResponseEntity<Void> deleteEmployee(@RequestBody EmployeeDto dto) throws EntityNotFoundException {
        employeeService.deleteEmployee(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
