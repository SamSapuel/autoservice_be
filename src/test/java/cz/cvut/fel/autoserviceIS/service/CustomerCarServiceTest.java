package cz.cvut.fel.autoserviceIS.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.cvut.fel.autoserviceIS.dto.CustomerCarDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.CustomerCarMapper;
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
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerCarService.class})
@ExtendWith(SpringExtension.class)
class CustomerCarServiceTest {
    @MockBean
    private CustomerCarMapper customerCarMapper;

    @MockBean
    private CustomerCarRepository customerCarRepository;

    @Autowired
    private CustomerCarService customerCarService;

    @MockBean
    private CustomerOrderItemRepository customerOrderItemRepository;

    @MockBean
    private CustomerOrderRepository customerOrderRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private EmployeesCustomersOrderRepository employeesCustomersOrderRepository;

    @Test
    void testGetAllCars() {
        when(customerCarRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(customerCarService.getAllCars().isEmpty());
        verify(customerCarRepository).findAll();
    }

    @Test
    void testFindById() throws EntityNotFoundException {
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

        CustomerCar customerCar = new CustomerCar();
        customerCar.setAge(1);
        customerCar.setCondition("Condition");
        customerCar.setCustomer(customer);
        customerCar.setId(1L);
        customerCar.setLicensePlate("License Plate");
        customerCar.setModel("Model");
        Optional<CustomerCar> ofResult = Optional.of(customerCar);
        when(customerCarRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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

        CustomerCarDto customerCarDto = new CustomerCarDto();
        customerCarDto.setAge(1);
        customerCarDto.setCondition("Condition");
        customerCarDto.setCustomer(customer2);
        customerCarDto.setId(1L);
        customerCarDto.setLicensePlate("License Plate");
        customerCarDto.setModel("Model");
        when(customerCarMapper.toDto(Mockito.<CustomerCar>any())).thenReturn(customerCarDto);
        assertSame(customerCarDto, customerCarService.findById(1L));
        verify(customerCarRepository).findById(Mockito.<Long>any());
        verify(customerCarMapper).toDto(Mockito.<CustomerCar>any());
    }

    @Test
    void testFindCustomerCars() {
        when(customerCarRepository.findCustomerCarByCustomer_Id(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        assertTrue(customerCarService.findCustomerCars(1L).isEmpty());
        verify(customerCarRepository).findCustomerCarByCustomer_Id(Mockito.<Long>any());
    }

    @Test
    void testCreateCar2() throws EntityNotFoundException {
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

        CustomerCar customerCar = new CustomerCar();
        customerCar.setAge(1);
        customerCar.setCondition("Condition");
        customerCar.setCustomer(customer);
        customerCar.setId(1L);
        customerCar.setLicensePlate("License Plate");
        customerCar.setModel("Model");
        when(customerCarRepository.save(Mockito.<CustomerCar>any())).thenReturn(customerCar);

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

        CustomerCar customerCar2 = new CustomerCar();
        customerCar2.setAge(1);
        customerCar2.setCondition("Condition");
        customerCar2.setCustomer(customer2);
        customerCar2.setId(1L);
        customerCar2.setLicensePlate("License Plate");
        customerCar2.setModel("Model");

        Customer customer3 = new Customer();
        customer3.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer3.setEmail("dima3@example.com");
        customer3.setFirstName("Dmitriy");
        customer3.setId(1L);
        customer3.setInfo("Info");
        customer3.setPassword("test");
        customer3.setPhone("728987345");
        customer3.setSecondName("Second Name");
        customer3.setUsername("dima3");

        CustomerCarDto customerCarDto = new CustomerCarDto();
        customerCarDto.setAge(1);
        customerCarDto.setCondition("Condition");
        customerCarDto.setCustomer(customer3);
        customerCarDto.setId(1L);
        customerCarDto.setLicensePlate("License Plate");
        customerCarDto.setModel("Model");
        when(customerCarMapper.toDto(Mockito.<CustomerCar>any())).thenReturn(customerCarDto);
        when(customerCarMapper.toEntity(Mockito.<CustomerCarDto>any())).thenReturn(customerCar2);
        Customer customer4 = mock(Customer.class);
        doNothing().when(customer4).setAccessType(Mockito.<AccessType>any());
        doNothing().when(customer4).setEmail(Mockito.<String>any());
        doNothing().when(customer4).setFirstName(Mockito.<String>any());
        doNothing().when(customer4).setId(anyLong());
        doNothing().when(customer4).setPassword(Mockito.<String>any());
        doNothing().when(customer4).setPhone(Mockito.<String>any());
        doNothing().when(customer4).setSecondName(Mockito.<String>any());
        doNothing().when(customer4).setUsername(Mockito.<String>any());
        doNothing().when(customer4).setInfo(Mockito.<String>any());
        customer4.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer4.setEmail("dima4@example.com");
        customer4.setFirstName("Dmitriy");
        customer4.setId(1L);
        customer4.setInfo("Info");
        customer4.setPassword("test");
        customer4.setPhone("728987345");
        customer4.setSecondName("Second Name");
        customer4.setUsername("dima4");
        CustomerCarDto dto = mock(CustomerCarDto.class);
        doNothing().when(dto).setAge(anyInt());
        doNothing().when(dto).setCondition(Mockito.<String>any());
        doNothing().when(dto).setCustomer(Mockito.<Customer>any());
        doNothing().when(dto).setId(anyLong());
        doNothing().when(dto).setLicensePlate(Mockito.<String>any());
        doNothing().when(dto).setModel(Mockito.<String>any());
        dto.setAge(1);
        dto.setCondition("Condition");
        dto.setCustomer(customer4);
        dto.setId(1L);
        dto.setLicensePlate("License Plate");
        dto.setModel("Model");

        Customer user = new Customer();
        user.setAccessType(AccessType.CUSTOMER_ACCESS);
        user.setEmail("dima@example.com");
        user.setFirstName("Dmitriy");
        user.setId(1L);
        user.setInfo("Info");
        user.setPassword("test");
        user.setPhone("728987345");
        user.setSecondName("Second Name");
        user.setUsername("dima");
        assertSame(customerCarDto, customerCarService.createCar(dto, new UserDetailsImpl(user)));
        verify(customerCarRepository).save(Mockito.<CustomerCar>any());
        verify(customerCarMapper).toDto(Mockito.<CustomerCar>any());
        verify(customerCarMapper).toEntity(Mockito.<CustomerCarDto>any());
        verify(dto).setAge(anyInt());
        verify(dto).setCondition(Mockito.<String>any());
        verify(dto).setCustomer(Mockito.<Customer>any());
        verify(dto).setId(anyLong());
        verify(dto).setLicensePlate(Mockito.<String>any());
        verify(dto).setModel(Mockito.<String>any());
        verify(customer4).setAccessType(Mockito.<AccessType>any());
        verify(customer4).setEmail(Mockito.<String>any());
        verify(customer4).setFirstName(Mockito.<String>any());
        verify(customer4).setId(anyLong());
        verify(customer4).setPassword(Mockito.<String>any());
        verify(customer4).setPhone(Mockito.<String>any());
        verify(customer4).setSecondName(Mockito.<String>any());
        verify(customer4).setUsername(Mockito.<String>any());
        verify(customer4).setInfo(Mockito.<String>any());
    }
    @Test
    void testDeleteCar2() throws EntityNotFoundException {
        doNothing().when(customerCarRepository).delete(Mockito.<CustomerCar>any());
        when(customerCarRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());

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
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(customerOrderRepository.findCustomerOrderByCustomer_Id(Mockito.<Long>any())).thenReturn(new ArrayList<>());

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

        CustomerCarDto dto = new CustomerCarDto();
        dto.setAge(1);
        dto.setCondition("Condition");
        dto.setCustomer(customer2);
        dto.setId(1L);
        dto.setLicensePlate("License Plate");
        dto.setModel("Model");
        assertThrows(EntityNotFoundException.class, () -> customerCarService.deleteCar(dto));
        verify(customerCarRepository).findById(Mockito.<Long>any());
        verify(customerRepository).findById(Mockito.<Long>any());
    }

}

