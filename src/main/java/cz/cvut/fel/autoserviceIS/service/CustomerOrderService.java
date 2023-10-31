package cz.cvut.fel.autoserviceIS.service;

import cz.cvut.fel.autoserviceIS.dto.CustomerOrderDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.CustomerCarMapper;
import cz.cvut.fel.autoserviceIS.mapper.CustomerOrderMapper;
import cz.cvut.fel.autoserviceIS.model.*;
import cz.cvut.fel.autoserviceIS.repository.CustomerOrderItemRepository;
import cz.cvut.fel.autoserviceIS.repository.CustomerOrderRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeesCustomersOrderRepository;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerOrderItemRepository customerOrderItemRepository;
    private final EmployeesCustomersOrderRepository employeesCustomersOrderRepository;
    private final CustomerOrderMapper customerOrderMapper;
    private final CustomerCarService customerCarService;
    private final CustomerCarMapper customerCarMapper;

    @Transactional
    public List<CustomerOrderDto> findAll() {
        List<CustomerOrder> orders = customerOrderRepository.findAll();
        return orders.stream()
                .map(customerOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CustomerOrderDto> getCurrentCustomersOrders(UserDetailsImpl userDetails) {
        List<CustomerOrder> orders = customerOrderRepository.findCustomerOrderByCustomer_Id(userDetails.getUser().getId());
        return orders.stream()
                .map(customerOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerOrderDto findById(Long id) throws EntityNotFoundException {
        CustomerOrder order = customerOrderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order with id: " + id + " not found"));
        CustomerOrderDto res = customerOrderMapper.toDto(order);
        return res;
    }

    @Transactional
    public CustomerOrderDto createOrder(CustomerOrderDto dto, UserDetailsImpl userDetails) throws EntityNotFoundException {
        CustomerCar customerCar = customerCarMapper
                .toEntity(customerCarService.findById(dto.getCustomerCar().getId()));
        CustomerOrder order = customerOrderMapper.toEntity(dto);
        order.setCustomer((Customer) userDetails.getUser());
        order.setCustomerCar(customerCar);
        order = customerOrderRepository.save(order);
        EmployeesCustomersOrder employeesCustomersOrder = new EmployeesCustomersOrder();
        employeesCustomersOrder.setOrder(order);
        employeesCustomersOrderRepository.save(employeesCustomersOrder);
        return customerOrderMapper.toDto(order);
    }

    @Transactional
    public CustomerOrderDto updateTimeOfOrder(CustomerOrderDto dto) throws EntityNotFoundException {
        CustomerOrder customerOrder = customerOrderRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Order with id:"  + dto.getId() + " not found"));
        customerOrder.setDateOfOrder(dto.getDateOfOrder());
        customerOrder = customerOrderRepository.save(customerOrder);
        return customerOrderMapper.toDto(customerOrder);
    }

    @Transactional
    public CustomerOrderDto updateStatusOfOrder(CustomerOrderDto dto) throws EntityNotFoundException {
        CustomerOrder customerOrder = customerOrderRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Order with id: " + dto.getId() + " not found"));
        customerOrder.setStatus(dto.getStatus());
        customerOrder = customerOrderRepository.save(customerOrder);
        return customerOrderMapper.toDto(customerOrder);
    }

    @Transactional
    public void deleteOrder(CustomerOrderDto dto) throws EntityNotFoundException {
        CustomerOrder customerOrder = customerOrderRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Order with id: " + dto.getId() + " not found"));
        EmployeesCustomersOrder employeesCustomersOrder = employeesCustomersOrderRepository.findEmployeesCustomersOrderByOrder_Id(customerOrder.getId());
        List<CustomerOrderItem> items = customerOrder.getOrderItems();
        for (CustomerOrderItem item : items) {
            CustomerOrderItem itemToDelete = item;
            item = null;
            customerOrderItemRepository.delete(itemToDelete);
        }
        employeesCustomersOrderRepository.delete(employeesCustomersOrder);
        customerOrderRepository.delete(customerOrder);
    }

}
