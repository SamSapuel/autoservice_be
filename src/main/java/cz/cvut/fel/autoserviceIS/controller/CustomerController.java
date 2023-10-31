package cz.cvut.fel.autoserviceIS.controller;

import cz.cvut.fel.autoserviceIS.dto.CustomerDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.model.Customer;
import cz.cvut.fel.autoserviceIS.security.CurrentUser;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import cz.cvut.fel.autoserviceIS.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ACCESS')")
    public CustomerDto getCustomerById(@PathVariable long id) throws EntityNotFoundException {
        CustomerDto customerDto = customerService.findById(id);
        return customerDto;
    }

    @GetMapping("/currentCustomer")
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS')")
    public ResponseEntity<?> getCurrentUser(@CurrentUser UserDetailsImpl userDetails) {
        Customer customer = (Customer) userDetails.getUser();
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ACCESS')")
    public List<CustomerDto> getAllCustomers() {
        return customerService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> saveCustomer(CustomerDto dto) {
        CustomerDto customerDto = customerService.addCustomer(dto);
        return ResponseEntity.ok(customerDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDto dto) throws EntityNotFoundException {
        CustomerDto customerDto = customerService.updateCustomer(dto);
        return ResponseEntity.ok(customerDto);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<Void> deleteCustomer(@RequestBody CustomerDto dto) throws EntityNotFoundException {
        customerService.deleteCustomer(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
