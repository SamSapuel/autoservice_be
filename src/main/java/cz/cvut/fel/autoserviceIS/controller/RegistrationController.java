package cz.cvut.fel.autoserviceIS.controller;

import cz.cvut.fel.autoserviceIS.dto.CustomerDto;
import cz.cvut.fel.autoserviceIS.dto.EmployeeDto;
import cz.cvut.fel.autoserviceIS.model.enums.AccessType;
import cz.cvut.fel.autoserviceIS.security.model.RegistrationRequest;
import cz.cvut.fel.autoserviceIS.security.model.RegistrationRequestEmployee;
import cz.cvut.fel.autoserviceIS.security.model.Response;
import cz.cvut.fel.autoserviceIS.service.CustomerService;
import cz.cvut.fel.autoserviceIS.service.EmployeeService;
import cz.cvut.fel.autoserviceIS.utils.RestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class RegistrationController {
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) throws EntityNotFoundException {
        try {
            String accessType = request.getAccessType();
            if (accessType.equals("customer_access")) {
                if((customerService.findByUsername(request.getUsername()) != null) ||
                        (customerService.findByEmail(request.getEmail()) != null)){
                    return new ResponseEntity<>(new Response(false, "Bad Credentials!"),
                            HttpStatus.BAD_REQUEST);
                }
                CustomerDto user = new CustomerDto();
                user.setFirstName(request.getFirstName());
                user.setSecondName(request.getSecondName());
                user.setUsername(request.getUsername());
                user.setEmail(request.getEmail());
                user.setPhone(request.getPhone());
                user.setAccessType(AccessType.CUSTOMER_ACCESS);
                user.setPassword(request.getPassword());
                user.setInfo(request.getInfo());
                customerService.addCustomer(user);
                log.debug("User {} successfully registered.", user);
            } else {
                return new ResponseEntity<>(new Response(false, "Invalid access type!"),
                        HttpStatus.BAD_REQUEST);
            }

            final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/users/current");
            return new ResponseEntity<>(headers, HttpStatus.CREATED);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new Response(false, "Entity not found!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/registrationEmp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerEmployee(@RequestBody RegistrationRequestEmployee request) {
        try {
            String accessType = request.getAccessType();
            if (accessType.equals("EMPLOYEE_ACCESS") || accessType.equals("ADMIN_ACCESS")) {
                if((employeeService.findByUsername(request.getUsername()) != null) ||
                        (employeeService.findByEmail(request.getEmail()) != null)){
                    return new ResponseEntity<>(new Response(false, "Bad Credentials!"),
                            HttpStatus.BAD_REQUEST);
                }
                EmployeeDto user = new EmployeeDto();
                user.setFirstName(request.getFirstName());
                user.setSecondName(request.getSecondName());
                user.setUsername(request.getUsername());
                user.setEmail(request.getEmail());
                user.setPhone(request.getPhone());
                user.setAccessType(accessType.equals("EMPLOYEE_ACCESS") ? AccessType.EMPLOYEE_ACCESS : AccessType.ADMIN_ACCESS);
                user.setPassword(request.getPassword());
                user.setSpecialization(request.getSpecialization());
                employeeService.addEmployee(user);
                log.debug("User {} successfully registered.", user);
            } else {
                return new ResponseEntity<>(new Response(false, "Invalid access type!"),
                        HttpStatus.BAD_REQUEST);
            }

            final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/users/current");
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }  catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new Response(false, "Entity not found!"), HttpStatus.BAD_REQUEST);
        }
    }
}
