package cz.cvut.fel.autoserviceIS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.cvut.fel.autoserviceIS.dto.CustomerOrderDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.CustomerCarMapper;
import cz.cvut.fel.autoserviceIS.mapper.CustomerOrderMapper;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerOrderService.class})
@ExtendWith(SpringExtension.class)
class CustomerOrderServiceTest {
    @MockBean
    private CustomerCarMapper customerCarMapper;

    @MockBean
    private CustomerCarService customerCarService;

    @MockBean
    private CustomerOrderItemRepository customerOrderItemRepository;

    @MockBean
    private CustomerOrderMapper customerOrderMapper;

    @MockBean
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private CustomerOrderService customerOrderService;

    @MockBean
    private EmployeesCustomersOrderRepository employeesCustomersOrderRepository;
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

        CustomerCar customerCar = new CustomerCar();
        customerCar.setAge(1);
        customerCar.setCondition("Condition");
        customerCar.setCustomer(customer2);
        customerCar.setId(1L);
        customerCar.setLicensePlate("License Plate");
        customerCar.setModel("Model");

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCreationTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        customerOrder.setCustomer(customer);
        customerOrder.setCustomerCar(customerCar);
        customerOrder.setDateOfOrder(LocalDate.of(1970, 1, 1).atStartOfDay());
        customerOrder.setId(1L);
        customerOrder.setOrderItems(new ArrayList<>());
        customerOrder.setStatus(OrdersStatus.IN_PROCESSING);
        Optional<CustomerOrder> ofResult = Optional.of(customerOrder);
        when(customerOrderRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Customer customer3 = new Customer();
        customer3.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer3.setEmail("dima3@example.com");
        customer3.setFirstName("Jane");
        customer3.setId(1L);
        customer3.setInfo("Info");
        customer3.setPassword("test");
        customer3.setPhone("728987345");
        customer3.setSecondName("Second Name");
        customer3.setUsername("dima3");

        Customer customer4 = new Customer();
        customer4.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer4.setEmail("dima4@example.com");
        customer4.setFirstName("Jane");
        customer4.setId(1L);
        customer4.setInfo("Info");
        customer4.setPassword("test");
        customer4.setPhone("728987345");
        customer4.setSecondName("Second Name");
        customer4.setUsername("dima4");

        CustomerCar customerCar2 = new CustomerCar();
        customerCar2.setAge(1);
        customerCar2.setCondition("Condition");
        customerCar2.setCustomer(customer4);
        customerCar2.setId(1L);
        customerCar2.setLicensePlate("License Plate");
        customerCar2.setModel("Model");

        CustomerOrderDto customerOrderDto = new CustomerOrderDto();
        customerOrderDto.setCreationTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        customerOrderDto.setCustomer(customer3);
        customerOrderDto.setCustomerCar(customerCar2);
        customerOrderDto.setDateOfOrder(LocalDate.of(1970, 1, 1).atStartOfDay());
        customerOrderDto.setId(1L);
        customerOrderDto.setOrderItems(new ArrayList<>());
        customerOrderDto.setStatus(OrdersStatus.IN_PROCESSING);
        when(customerOrderMapper.toDto(Mockito.<CustomerOrder>any())).thenReturn(customerOrderDto);
        assertSame(customerOrderDto, customerOrderService.findById(1L));
        verify(customerOrderRepository).findById(Mockito.<Long>any());
        verify(customerOrderMapper).toDto(Mockito.<CustomerOrder>any());
    }
    @Test
    void testFindById2()  {
        when(customerOrderRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());

        Customer customer = new Customer();
        customer.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer.setEmail("jane.doe@example.org");
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setInfo("Info");
        customer.setPassword("iloveyou");
        customer.setPhone("728987345");
        customer.setSecondName("Second Name");
        customer.setUsername("janedoe");

        Customer customer2 = new Customer();
        customer2.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer2.setEmail("jane.doe@example.org");
        customer2.setFirstName("Jane");
        customer2.setId(1L);
        customer2.setInfo("Info");
        customer2.setPassword("iloveyou");
        customer2.setPhone("728987345");
        customer2.setSecondName("Second Name");
        customer2.setUsername("janedoe");

        CustomerCar customerCar = new CustomerCar();
        customerCar.setAge(1);
        customerCar.setCondition("Condition");
        customerCar.setCustomer(customer2);
        customerCar.setId(1L);
        customerCar.setLicensePlate("License Plate");
        customerCar.setModel("Model");

        CustomerOrderDto customerOrderDto = new CustomerOrderDto();
        customerOrderDto.setCreationTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        customerOrderDto.setCustomer(customer);
        customerOrderDto.setCustomerCar(customerCar);
        customerOrderDto.setDateOfOrder(LocalDate.of(1970, 1, 1).atStartOfDay());
        customerOrderDto.setId(1L);
        customerOrderDto.setOrderItems(new ArrayList<>());
        customerOrderDto.setStatus(OrdersStatus.IN_PROCESSING);
        when(customerOrderMapper.toDto(Mockito.<CustomerOrder>any())).thenReturn(customerOrderDto);
        assertThrows(EntityNotFoundException.class, () -> customerOrderService.findById(1L));
        verify(customerOrderRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testDeleteOrder() throws EntityNotFoundException {
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

        CustomerCar customerCar = new CustomerCar();
        customerCar.setAge(1);
        customerCar.setCondition("Condition");
        customerCar.setCustomer(customer2);
        customerCar.setId(1L);
        customerCar.setLicensePlate("License Plate");
        customerCar.setModel("Model");

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCreationTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        customerOrder.setCustomer(customer);
        customerOrder.setCustomerCar(customerCar);
        customerOrder.setDateOfOrder(LocalDate.of(1970, 1, 1).atStartOfDay());
        customerOrder.setId(1L);
        ArrayList<CustomerOrderItem> orderItems = new ArrayList<>();
        customerOrder.setOrderItems(orderItems);
        customerOrder.setStatus(OrdersStatus.IN_PROCESSING);
        Optional<CustomerOrder> ofResult = Optional.of(customerOrder);
        doNothing().when(customerOrderRepository).delete(Mockito.<CustomerOrder>any());
        when(customerOrderRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Employee employee = new Employee();
        employee.setAccessType(AccessType.CUSTOMER_ACCESS);
        employee.setEmail("dima3@example.com");
        employee.setFirstName("Dmitriy");
        employee.setId(1L);
        employee.setPassword("test");
        employee.setPhone("728987345");
        employee.setSecondName("Second Name");
        employee.setSpecialization("Specialization");
        employee.setUsername("dima3");

        Customer customer3 = new Customer();
        customer3.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer3.setEmail("dima4@example.com");
        customer3.setFirstName("Dmitriy");
        customer3.setId(1L);
        customer3.setInfo("Info");
        customer3.setPassword("iloveyou");
        customer3.setPhone("728987345");
        customer3.setSecondName("Second Name");
        customer3.setUsername("dima4");

        Customer customer4 = new Customer();
        customer4.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer4.setEmail("dima5@example.com");
        customer4.setFirstName("Dmitriy");
        customer4.setId(1L);
        customer4.setInfo("Info");
        customer4.setPassword("test");
        customer4.setPhone("728987345");
        customer4.setSecondName("Second Name");
        customer4.setUsername("dima5");

        CustomerCar customerCar2 = new CustomerCar();
        customerCar2.setAge(1);
        customerCar2.setCondition("Condition");
        customerCar2.setCustomer(customer4);
        customerCar2.setId(1L);
        customerCar2.setLicensePlate("License Plate");
        customerCar2.setModel("Model");

        CustomerOrder order = new CustomerOrder();
        order.setCreationTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        order.setCustomer(customer3);
        order.setCustomerCar(customerCar2);
        order.setDateOfOrder(LocalDate.of(1970, 1, 1).atStartOfDay());
        order.setId(1L);
        order.setOrderItems(new ArrayList<>());
        order.setStatus(OrdersStatus.IN_PROCESSING);

        EmployeesCustomersOrder employeesCustomersOrder = new EmployeesCustomersOrder();
        employeesCustomersOrder.setEmployee(employee);
        employeesCustomersOrder.setId(1L);
        employeesCustomersOrder.setOrder(order);
        when(employeesCustomersOrderRepository.findEmployeesCustomersOrderByOrder_Id(Mockito.<Long>any()))
                .thenReturn(employeesCustomersOrder);
        doNothing().when(employeesCustomersOrderRepository).delete(Mockito.<EmployeesCustomersOrder>any());

        Customer customer5 = new Customer();
        customer5.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer5.setEmail("dima6@example.com");
        customer5.setFirstName("Dmitriy");
        customer5.setId(1L);
        customer5.setInfo("Info");
        customer5.setPassword("test");
        customer5.setPhone("728987345");
        customer5.setSecondName("Second Name");
        customer5.setUsername("dima6");

        Customer customer6 = new Customer();
        customer6.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer6.setEmail("dima7@example.com");
        customer6.setFirstName("Dmitriy");
        customer6.setId(1L);
        customer6.setInfo("Info");
        customer6.setPassword("test");
        customer6.setPhone("728987345");
        customer6.setSecondName("Second Name");
        customer6.setUsername("dima7");

        CustomerCar customerCar3 = new CustomerCar();
        customerCar3.setAge(1);
        customerCar3.setCondition("Condition");
        customerCar3.setCustomer(customer6);
        customerCar3.setId(1L);
        customerCar3.setLicensePlate("License Plate");
        customerCar3.setModel("Model");

        CustomerOrderDto dto = new CustomerOrderDto();
        dto.setCreationTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        dto.setCustomer(customer5);
        dto.setCustomerCar(customerCar3);
        dto.setDateOfOrder(LocalDate.of(1970, 1, 1).atStartOfDay());
        dto.setId(1L);
        dto.setOrderItems(new ArrayList<>());
        dto.setStatus(OrdersStatus.IN_PROCESSING);
        customerOrderService.deleteOrder(dto);
        verify(customerOrderRepository).findById(Mockito.<Long>any());
        verify(customerOrderRepository).delete(Mockito.<CustomerOrder>any());
        verify(employeesCustomersOrderRepository).findEmployeesCustomersOrderByOrder_Id(Mockito.<Long>any());
        verify(employeesCustomersOrderRepository).delete(Mockito.<EmployeesCustomersOrder>any());
        assertEquals(OrdersStatus.IN_PROCESSING, dto.getStatus());
        assertEquals("00:00", dto.getCreationTime().toLocalTime().toString());
        assertEquals(orderItems, dto.getOrderItems());
        assertEquals(1L, dto.getId());
        assertSame(customerCar3, dto.getCustomerCar());
        assertEquals("00:00", dto.getDateOfOrder().toLocalTime().toString());
        assertSame(customer5, dto.getCustomer());
    }
}

