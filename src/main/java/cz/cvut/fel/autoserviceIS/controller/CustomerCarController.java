package cz.cvut.fel.autoserviceIS.controller;

import cz.cvut.fel.autoserviceIS.dto.CustomerCarDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.security.CurrentUser;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import cz.cvut.fel.autoserviceIS.service.CustomerCarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customerCar")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class CustomerCarController {

    private final CustomerCarService customerCarService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> getAllCars() {
        List<CustomerCarDto> cars = customerCarService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS', 'CUSTOMER_ACCESS')")
    public ResponseEntity<?> getCustomerCar(@PathVariable Long customerId) {
        List<CustomerCarDto> customerCarDtos = customerCarService.findCustomerCars(customerId);
        return ResponseEntity.ok(customerCarDtos);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> getCurrentCustomerCar(@CurrentUser UserDetailsImpl userDetails) {
        List<CustomerCarDto> customerCars = customerCarService.findCustomerCars(userDetails.getUser().getId());
        return ResponseEntity.ok(customerCars);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public ResponseEntity<?> saveCar(@RequestBody CustomerCarDto dto, @CurrentUser UserDetailsImpl userDetails) throws EntityNotFoundException {
        CustomerCarDto customerCarDto = customerCarService.createCar(dto, userDetails);
        return ResponseEntity.ok(customerCarDto);
    }

    @DeleteMapping("/{carId}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER_ACCESS', 'ADMIN_ACCESS')")
    public void deleteCar(@PathVariable Long carId) throws EntityNotFoundException {
        CustomerCarDto customerCar = customerCarService.findById(carId);
        customerCarService.deleteCar(customerCar);
    }
}
