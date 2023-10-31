package cz.cvut.fel.autoserviceIS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.cvut.fel.autoserviceIS.dto.CustomerDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.CustomerMapper;
import cz.cvut.fel.autoserviceIS.model.Customer;
import cz.cvut.fel.autoserviceIS.model.CustomerCar;
import cz.cvut.fel.autoserviceIS.model.CustomerOrder;
import cz.cvut.fel.autoserviceIS.model.CustomerOrderItem;
import cz.cvut.fel.autoserviceIS.model.Employee;
import cz.cvut.fel.autoserviceIS.model.EmployeesCustomersOrder;
import cz.cvut.fel.autoserviceIS.model.enums.AccessType;
import cz.cvut.fel.autoserviceIS.model.enums.OrdersStatus;
import cz.cvut.fel.autoserviceIS.repository.CustomerCarRepository;
import cz.cvut.fel.autoserviceIS.repository.CustomerOrderItemRepository;
import cz.cvut.fel.autoserviceIS.repository.CustomerOrderRepository;
import cz.cvut.fel.autoserviceIS.repository.CustomerRepository;
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

@ContextConfiguration(classes = {CustomerService.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    @MockBean
    private CustomerCarRepository customerCarRepository;

    @MockBean
    private CustomerMapper customerMapper;

    @MockBean
    private CustomerOrderItemRepository customerOrderItemRepository;

    @MockBean
    private CustomerOrderRepository customerOrderRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @MockBean
    private EmployeesCustomersOrderRepository employeesCustomersOrderRepository;
    @Test
    void testAddCustomer() {
        Customer customer = new Customer();
        customer.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer.setEmail("dima@example.com");
        customer.setFirstName("Dmitriy");
        customer.setId(1L);
        customer.setInfo("Info");
        customer.setPassword("test");
        customer.setPhone("728987345");
        customer.setSecondName("Second Name");
        customer.setUsername("dima");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer);

        Customer customer2 = new Customer();
        customer2.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer2.setEmail("dima2@example.com");
        customer2.setFirstName("Dmitriy");
        customer2.setId(1L);
        customer2.setInfo("Info");
        customer2.setPassword("test");
        customer2.setPhone("728987345");
        customer2.setSecondName("Second Name");
        customer2.setUsername("dima2");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccessType(AccessType.CUSTOMER_ACCESS);
        customerDto.setEmail("dima@example.com");
        customerDto.setFirstName("Dmitriy");
        customerDto.setId(1L);
        customerDto.setInfo("Info");
        customerDto.setPassword("test");
        customerDto.setPhone("728987345");
        customerDto.setSecondName("Second Name");
        customerDto.setUsername("dima");
        when(customerMapper.toDto(Mockito.<Customer>any())).thenReturn(customerDto);
        when(customerMapper.toEntity(Mockito.<CustomerDto>any())).thenReturn(customer2);

        CustomerDto dto = new CustomerDto();
        dto.setAccessType(AccessType.CUSTOMER_ACCESS);
        dto.setEmail("dima@example.com");
        dto.setFirstName("Dmitriy");
        dto.setId(1L);
        dto.setInfo("Info");
        dto.setPassword("test");
        dto.setPhone("728987345");
        dto.setSecondName("Second Name");
        dto.setUsername("dima");
        assertSame(customerDto, customerService.addCustomer(dto));
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerMapper).toDto(Mockito.<Customer>any());
        verify(customerMapper).toEntity(Mockito.<CustomerDto>any());
    }
    @Test
    void testUpdateCustomer() throws EntityNotFoundException {
        Customer customer = new Customer();
        customer.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer.setEmail("dima@example.com");
        customer.setFirstName("Dmitriy");
        customer.setId(1L);
        customer.setInfo("Info");
        customer.setPassword("test");
        customer.setPhone("728987345");
        customer.setSecondName("Second Name");
        customer.setUsername("dima");
        Optional<Customer> ofResult = Optional.of(customer);

        Customer customer2 = new Customer();
        customer2.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer2.setEmail("dima@example.com");
        customer2.setFirstName("Dmitriy");
        customer2.setId(1L);
        customer2.setInfo("Info");
        customer2.setPassword("test");
        customer2.setPhone("728987345");
        customer2.setSecondName("Second Name");
        customer2.setUsername("dima");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer2);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccessType(AccessType.CUSTOMER_ACCESS);
        customerDto.setEmail("dima@example.com");
        customerDto.setFirstName("Dmitriy");
        customerDto.setId(1L);
        customerDto.setInfo("Info");
        customerDto.setPassword("test");
        customerDto.setPhone("728987345");
        customerDto.setSecondName("Second Name");
        customerDto.setUsername("dima");
        when(customerMapper.toDto(Mockito.<Customer>any())).thenReturn(customerDto);

        CustomerDto dto = new CustomerDto();
        dto.setAccessType(AccessType.CUSTOMER_ACCESS);
        dto.setEmail("dima@example.com");
        dto.setFirstName("Dmitriy");
        dto.setId(1L);
        dto.setInfo("Info");
        dto.setPassword("test");
        dto.setPhone("728987345");
        dto.setSecondName("Second Name");
        dto.setUsername("dima");
        assertSame(customerDto, customerService.updateCustomer(dto));
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(customerMapper).toDto(Mockito.<Customer>any());
    }
    @Test
    void testDeleteCustomer()  {
        doNothing().when(customerRepository).delete(Mockito.<Customer>any());
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        when(customerOrderRepository.findCustomerOrderByCustomer_Id(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        doNothing().when(customerOrderRepository).deleteAll(Mockito.<Iterable<CustomerOrder>>any());
        when(customerCarRepository.findCustomerCarByCustomer_Id(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        doNothing().when(customerCarRepository).deleteAll(Mockito.<Iterable<CustomerCar>>any());

        CustomerDto dto = new CustomerDto();
        dto.setAccessType(AccessType.CUSTOMER_ACCESS);
        dto.setEmail("dima@example.com");
        dto.setFirstName("Dmitriy");
        dto.setId(1L);
        dto.setInfo("Info");
        dto.setPassword("test");
        dto.setPhone("6625550144");
        dto.setSecondName("Second Name");
        dto.setUsername("dima");
        assertThrows(EntityNotFoundException.class, () -> customerService.deleteCustomer(dto));
        verify(customerRepository).findById(Mockito.<Long>any());
    }



}

