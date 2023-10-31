package cz.cvut.fel.autoserviceIS.service;

import cz.cvut.fel.autoserviceIS.dto.CustomerOrderDto;
import cz.cvut.fel.autoserviceIS.dto.EmployeeDto;
import cz.cvut.fel.autoserviceIS.dto.EmployeesCustomersOrderDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.EmployeesCustomersOrderMapper;
import cz.cvut.fel.autoserviceIS.model.CustomerOrder;
import cz.cvut.fel.autoserviceIS.model.Employee;
import cz.cvut.fel.autoserviceIS.model.EmployeesCustomersOrder;
import cz.cvut.fel.autoserviceIS.repository.CustomerOrderRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeeRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeesCustomersOrderRepository;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeesCustomersOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final EmployeesCustomersOrderRepository employeesCustomersOrderRepository;
    private final EmployeesCustomersOrderMapper employeesCustomersOrderMapper;

    @Transactional
    public List<EmployeesCustomersOrderDto> getCurrentEmployeesCusOrders(UserDetailsImpl userDetails) {
        List<EmployeesCustomersOrder> orders = employeesCustomersOrderRepository.findEmployeesCustomersOrderByEmployee_Id(userDetails.getId());
        return orders.stream()
                .map(employeesCustomersOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<EmployeesCustomersOrderDto> getCustomerOrdersWithoutEmployee() {
        List<EmployeesCustomersOrder> orders = employeesCustomersOrderRepository.findAll().stream()
                .filter(order -> order.getEmployee() == null)
                .collect(Collectors.toList());
        return orders.stream()
                .map(employeesCustomersOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeesCustomersOrderDto connectEmployeeWithCustomerOrder(UserDetailsImpl userDetails, Long customerOrderId) throws EntityNotFoundException {
        CustomerOrder customerOrder = customerOrderRepository.findById(customerOrderId).orElseThrow(() -> new EntityNotFoundException("Order with id: " + customerOrderId + " not found"));
        EmployeesCustomersOrder employeesCustomersOrder = new EmployeesCustomersOrder();
        employeesCustomersOrder.setEmployee((Employee) userDetails.getUser());
        employeesCustomersOrder.setOrder(customerOrder);
        EmployeesCustomersOrder res = employeesCustomersOrderRepository.save(employeesCustomersOrder);
        return employeesCustomersOrderMapper.toDto(res);
    }

    @Transactional
    public EmployeesCustomersOrderDto updateConnectionBetweenEmployeeAndCusOrder(UserDetailsImpl userDetails, Long employeesCustomersOrderId) throws EntityNotFoundException {
        EmployeesCustomersOrder employeesCustomersOrder = employeesCustomersOrderRepository.findById(employeesCustomersOrderId).orElseThrow(() -> new EntityNotFoundException("Connection with id: " + employeesCustomersOrderId + " not found"));
        employeesCustomersOrder.setEmployee((Employee) userDetails.getUser());
        EmployeesCustomersOrder res = employeesCustomersOrderRepository.save(employeesCustomersOrder);
        return employeesCustomersOrderMapper.toDto(res);
    }

    @Transactional
    public void deleteConnectionBetweenEmployeeAndCusOrder(Long id) throws EntityNotFoundException {
        EmployeesCustomersOrder employeesCustomersOrder = employeesCustomersOrderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Connection with id: " + id + " not found"));
        employeesCustomersOrder.setEmployee(null);
        employeesCustomersOrder.setOrder(null);
        employeesCustomersOrderRepository.delete(employeesCustomersOrder);
    }
}
