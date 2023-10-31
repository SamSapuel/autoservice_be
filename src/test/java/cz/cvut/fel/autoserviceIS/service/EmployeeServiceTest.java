package cz.cvut.fel.autoserviceIS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.cvut.fel.autoserviceIS.dto.EmployeeDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.EmployeeMapper;
import cz.cvut.fel.autoserviceIS.model.Employee;
import cz.cvut.fel.autoserviceIS.model.EmployeeOrder;
import cz.cvut.fel.autoserviceIS.model.enums.AccessType;
import cz.cvut.fel.autoserviceIS.model.enums.OrdersStatus;
import cz.cvut.fel.autoserviceIS.repository.EmployeeOrderItemRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeeOrderRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeeRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeesCustomersOrderRepository;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmployeeService.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {
    @MockBean
    private EmployeeMapper employeeMapper;

    @MockBean
    private EmployeeOrderItemRepository employeeOrderItemRepository;

    @MockBean
    private EmployeeOrderRepository employeeOrderRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeesCustomersOrderRepository employeesCustomersOrderRepository;

    @Test
    void testAddEmployee() {
        Employee employee = new Employee();
        employee.setAccessType(AccessType.CUSTOMER_ACCESS);
        employee.setEmail("dima@example.com");
        employee.setFirstName("Dmitriy");
        employee.setId(1L);
        employee.setPassword("test");
        employee.setPhone("728987345");
        employee.setSecondName("Second Name");
        employee.setSpecialization("Specialization");
        employee.setUsername("dima");
        when(employeeRepository.save(Mockito.<Employee>any())).thenReturn(employee);

        Employee employee2 = new Employee();
        employee2.setAccessType(AccessType.CUSTOMER_ACCESS);
        employee2.setEmail("dima2@example.com");
        employee2.setFirstName("Dmitriy");
        employee2.setId(1L);
        employee2.setPassword("test");
        employee2.setPhone("728987345");
        employee2.setSecondName("Second Name");
        employee2.setSpecialization("Specialization");
        employee2.setUsername("dima2");

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setAccessType(AccessType.CUSTOMER_ACCESS);
        employeeDto.setEmail("dima@example.com");
        employeeDto.setFirstName("Dmitriy");
        employeeDto.setId(1L);
        employeeDto.setPassword("test");
        employeeDto.setPhone("728987345");
        employeeDto.setSecondName("Second Name");
        employeeDto.setSpecialization("Specialization");
        employeeDto.setUsername("dima");
        when(employeeMapper.toDto(Mockito.<Employee>any())).thenReturn(employeeDto);
        when(employeeMapper.toEntity(Mockito.<EmployeeDto>any())).thenReturn(employee2);

        EmployeeDto dto = new EmployeeDto();
        dto.setAccessType(AccessType.CUSTOMER_ACCESS);
        dto.setEmail("dima@example.com");
        dto.setFirstName("Dmitriy");
        dto.setId(1L);
        dto.setPassword("test");
        dto.setPhone("728987345");
        dto.setSecondName("Second Name");
        dto.setSpecialization("Specialization");
        dto.setUsername("dima");
        assertSame(employeeDto, employeeService.addEmployee(dto));
        verify(employeeRepository).save(Mockito.<Employee>any());
        verify(employeeMapper).toDto(Mockito.<Employee>any());
        verify(employeeMapper).toEntity(Mockito.<EmployeeDto>any());
    }

    @Test
    void testUpdateEmployee() throws EntityNotFoundException {
        Employee employee = new Employee();
        employee.setAccessType(AccessType.CUSTOMER_ACCESS);
        employee.setEmail("dima@example.com");
        employee.setFirstName("Dmitriy");
        employee.setId(1L);
        employee.setPassword("test");
        employee.setPhone("728987345");
        employee.setSecondName("Second Name");
        employee.setSpecialization("Specialization");
        employee.setUsername("dima");
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee2 = new Employee();
        employee2.setAccessType(AccessType.CUSTOMER_ACCESS);
        employee2.setEmail("dima2@example.com");
        employee2.setFirstName("Dmitriy");
        employee2.setId(1L);
        employee2.setPassword("test");
        employee2.setPhone("728987345");
        employee2.setSecondName("Second Name");
        employee2.setSpecialization("Specialization");
        employee2.setUsername("dima2");
        when(employeeRepository.save(Mockito.<Employee>any())).thenReturn(employee2);
        when(employeeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setAccessType(AccessType.CUSTOMER_ACCESS);
        employeeDto.setEmail("dima@example.com");
        employeeDto.setFirstName("Dmitriy");
        employeeDto.setId(1L);
        employeeDto.setPassword("test");
        employeeDto.setPhone("728987345");
        employeeDto.setSecondName("Second Name");
        employeeDto.setSpecialization("Specialization");
        employeeDto.setUsername("dima");
        when(employeeMapper.toDto(Mockito.<Employee>any())).thenReturn(employeeDto);

        EmployeeDto dto = new EmployeeDto();
        dto.setAccessType(AccessType.CUSTOMER_ACCESS);
        dto.setEmail("dima@example.com");
        dto.setFirstName("Dmitriy");
        dto.setId(1L);
        dto.setPassword("test");
        dto.setPhone("728987345");
        dto.setSecondName("Second Name");
        dto.setSpecialization("Specialization");
        dto.setUsername("dima");
        assertSame(employeeDto, employeeService.updateEmployee(dto));
        verify(employeeRepository).save(Mockito.<Employee>any());
        verify(employeeRepository).findById(Mockito.<Long>any());
        verify(employeeMapper).toDto(Mockito.<Employee>any());
    }

    @Test
    void testDeleteEmployee2() throws EntityNotFoundException {
        doNothing().when(employeeRepository).delete(Mockito.<Employee>any());
        when(employeeRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        when(employeeOrderRepository.findEmployeeOrderByEmployee_Id(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        when(employeesCustomersOrderRepository.findEmployeesCustomersOrderByEmployee_Id(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        EmployeeDto dto = new EmployeeDto();
        dto.setAccessType(AccessType.CUSTOMER_ACCESS);
        dto.setEmail("dima@example.com");
        dto.setFirstName("Dmitriy");
        dto.setId(1L);
        dto.setPassword("test");
        dto.setPhone("728987345");
        dto.setSecondName("Second Name");
        dto.setSpecialization("Specialization");
        dto.setUsername("dima");
        assertThrows(EntityNotFoundException.class, () -> employeeService.deleteEmployee(dto));
        verify(employeeRepository).findById(Mockito.<Long>any());
    }
}

