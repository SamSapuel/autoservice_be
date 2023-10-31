package cz.cvut.fel.autoserviceIS.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.cvut.fel.autoserviceIS.dto.EmployeeOrderDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.EmployeeOrderMapper;
import cz.cvut.fel.autoserviceIS.mapper.ItemMapper;
import cz.cvut.fel.autoserviceIS.model.Employee;
import cz.cvut.fel.autoserviceIS.model.EmployeeOrder;
import cz.cvut.fel.autoserviceIS.model.EmployeeOrderItem;
import cz.cvut.fel.autoserviceIS.model.Item;
import cz.cvut.fel.autoserviceIS.model.enums.AccessType;
import cz.cvut.fel.autoserviceIS.model.enums.OrdersStatus;
import cz.cvut.fel.autoserviceIS.repository.EmployeeOrderItemRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeeOrderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmployeeOrderService.class})
@ExtendWith(SpringExtension.class)
class EmployeeOrderServiceTest {
    @MockBean
    private EmployeeOrderItemRepository employeeOrderItemRepository;

    @MockBean
    private EmployeeOrderMapper employeeOrderMapper;

    @MockBean
    private EmployeeOrderRepository employeeOrderRepository;

    @Autowired
    private EmployeeOrderService employeeOrderService;

    @MockBean
    private ItemMapper itemMapper;

    @MockBean
    private ItemService itemService;

    @Test
    void testDeleteOrder() throws EntityNotFoundException {
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

        EmployeeOrder employeeOrder = new EmployeeOrder();
        employeeOrder.setCreationTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        employeeOrder.setEmployee(employee);
        employeeOrder.setId(1L);
        employeeOrder.setOrderItems(new ArrayList<>());
        employeeOrder.setStatus(OrdersStatus.IN_PROCESSING);
        Optional<EmployeeOrder> ofResult = Optional.of(employeeOrder);
        doNothing().when(employeeOrderRepository).delete(Mockito.<EmployeeOrder>any());
        when(employeeOrderRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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

        EmployeeOrderDto dto = new EmployeeOrderDto();
        dto.setCreationTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        dto.setEmployee(employee2);
        dto.setId(1L);
        dto.setOrderItems(new ArrayList<>());
        dto.setStatus(OrdersStatus.IN_PROCESSING);
        employeeOrderService.deleteOrder(dto);
        verify(employeeOrderRepository).findById(Mockito.<Long>any());
        verify(employeeOrderRepository).delete(Mockito.<EmployeeOrder>any());
    }

    @Test
    void testDeleteOrder2() throws EntityNotFoundException {
        doNothing().when(employeeOrderRepository).delete(Mockito.<EmployeeOrder>any());
        when(employeeOrderRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());

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

        EmployeeOrderDto dto = new EmployeeOrderDto();
        dto.setCreationTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        dto.setEmployee(employee);
        dto.setId(1L);
        dto.setOrderItems(new ArrayList<>());
        dto.setStatus(OrdersStatus.IN_PROCESSING);
        assertThrows(EntityNotFoundException.class, () -> employeeOrderService.deleteOrder(dto));
        verify(employeeOrderRepository).findById(Mockito.<Long>any());
    }
}

