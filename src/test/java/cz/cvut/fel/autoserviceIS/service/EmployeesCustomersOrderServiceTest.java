package cz.cvut.fel.autoserviceIS.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.EmployeesCustomersOrderMapper;
import cz.cvut.fel.autoserviceIS.model.Customer;
import cz.cvut.fel.autoserviceIS.model.CustomerCar;
import cz.cvut.fel.autoserviceIS.model.CustomerOrder;
import cz.cvut.fel.autoserviceIS.model.CustomerOrderItem;
import cz.cvut.fel.autoserviceIS.model.Employee;
import cz.cvut.fel.autoserviceIS.model.EmployeesCustomersOrder;
import cz.cvut.fel.autoserviceIS.model.enums.AccessType;
import cz.cvut.fel.autoserviceIS.model.enums.OrdersStatus;
import cz.cvut.fel.autoserviceIS.repository.CustomerOrderRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeesCustomersOrderRepository;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
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

@ContextConfiguration(classes = {EmployeesCustomersOrderService.class})
@ExtendWith(SpringExtension.class)
class EmployeesCustomersOrderServiceTest {
    @MockBean
    private CustomerOrderRepository customerOrderRepository;

    @MockBean
    private EmployeesCustomersOrderMapper employeesCustomersOrderMapper;

    @MockBean
    private EmployeesCustomersOrderRepository employeesCustomersOrderRepository;

    @Autowired
    private EmployeesCustomersOrderService employeesCustomersOrderService;

    @Test
    void testGetCustomerOrdersWithoutEmployee() {
        when(employeesCustomersOrderRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(employeesCustomersOrderService.getCustomerOrdersWithoutEmployee().isEmpty());
        verify(employeesCustomersOrderRepository).findAll();
    }

    @Test
    void testGetCustomerOrdersWithoutEmployee2() {
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

        CustomerOrder order = new CustomerOrder();
        order.setCreationTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        order.setCustomer(customer);
        order.setCustomerCar(customerCar);
        order.setDateOfOrder(LocalDate.of(1970, 1, 1).atStartOfDay());
        order.setId(1L);
        order.setOrderItems(new ArrayList<>());
        order.setStatus(OrdersStatus.IN_PROCESSING);

        EmployeesCustomersOrder employeesCustomersOrder = new EmployeesCustomersOrder();
        employeesCustomersOrder.setEmployee(employee);
        employeesCustomersOrder.setId(1L);
        employeesCustomersOrder.setOrder(order);

        ArrayList<EmployeesCustomersOrder> employeesCustomersOrderList = new ArrayList<>();
        employeesCustomersOrderList.add(employeesCustomersOrder);
        when(employeesCustomersOrderRepository.findAll()).thenReturn(employeesCustomersOrderList);
        assertTrue(employeesCustomersOrderService.getCustomerOrdersWithoutEmployee().isEmpty());
        verify(employeesCustomersOrderRepository).findAll();
    }

    @Test
    void testDeleteConnectionBetweenEmployeeAndCusOrder() throws EntityNotFoundException {
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
        customer2.setUsername("dima");

        CustomerCar customerCar = new CustomerCar();
        customerCar.setAge(1);
        customerCar.setCondition("Condition");
        customerCar.setCustomer(customer2);
        customerCar.setId(1L);
        customerCar.setLicensePlate("License Plate");
        customerCar.setModel("Model");

        CustomerOrder order = new CustomerOrder();
        order.setCreationTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        order.setCustomer(customer);
        order.setCustomerCar(customerCar);
        order.setDateOfOrder(LocalDate.of(1970, 1, 1).atStartOfDay());
        order.setId(1L);
        order.setOrderItems(new ArrayList<>());
        order.setStatus(OrdersStatus.IN_PROCESSING);

        EmployeesCustomersOrder employeesCustomersOrder = new EmployeesCustomersOrder();
        employeesCustomersOrder.setEmployee(employee);
        employeesCustomersOrder.setId(1L);
        employeesCustomersOrder.setOrder(order);
        Optional<EmployeesCustomersOrder> ofResult = Optional.of(employeesCustomersOrder);
        doNothing().when(employeesCustomersOrderRepository).delete(Mockito.<EmployeesCustomersOrder>any());
        when(employeesCustomersOrderRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        employeesCustomersOrderService.deleteConnectionBetweenEmployeeAndCusOrder(1L);
        verify(employeesCustomersOrderRepository).findById(Mockito.<Long>any());
        verify(employeesCustomersOrderRepository).delete(Mockito.<EmployeesCustomersOrder>any());
    }
}

