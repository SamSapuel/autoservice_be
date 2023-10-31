package cz.cvut.fel.autoserviceIS.service;

import cz.cvut.fel.autoserviceIS.dto.CustomerOrderItemDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.CustomerOrderItemMapper;
import cz.cvut.fel.autoserviceIS.model.CustomerOrder;
import cz.cvut.fel.autoserviceIS.model.CustomerOrderItem;
import cz.cvut.fel.autoserviceIS.model.EmployeesCustomersOrder;
import cz.cvut.fel.autoserviceIS.repository.CustomerOrderItemRepository;
import cz.cvut.fel.autoserviceIS.repository.CustomerOrderRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeesCustomersOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerOrderItemService {

    private final CustomerOrderItemRepository customerOrderItemRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerOrderItemMapper customerOrderItemMapper;
    //private final EmployeesCustomersOrderRepository employeesCustomersOrderRepository;

    @Transactional
    public CustomerOrderItemDto findById(Long id) throws EntityNotFoundException {
        CustomerOrderItem customerOrderItem = customerOrderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order item with id:" + id + "not found"));
        CustomerOrderItemDto res = customerOrderItemMapper.toDto(customerOrderItem);
        return res;
    }

    @Transactional
    public List<CustomerOrderItemDto> createCustomerOrderItem(Long orderId, List<CustomerOrderItemDto> dtos) throws EntityNotFoundException {
        List<CustomerOrderItem> customerOrderItems = dtos.stream()
                .map(customerOrderItemMapper::toEntity)
                .collect(Collectors.toList());
        CustomerOrder customerOrder = customerOrderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found"));
        customerOrderItemRepository.saveAll(customerOrderItems);
        customerOrderItemRepository.deleteAll(customerOrder.getOrderItems());
        customerOrder.setOrderItems(customerOrderItems);
        customerOrderRepository.save(customerOrder);
        List<CustomerOrderItemDto> res = customerOrderItems.stream()
                .map(customerOrderItemMapper::toDto)
                .collect(Collectors.toList());
        return res;
    }

    @Transactional
    public CustomerOrderItemDto updateCustomerOrderItemDescriptionOrTotalPrice(Long orderId, CustomerOrderItemDto dto) throws EntityNotFoundException {
        CustomerOrderItem customerOrderItem = customerOrderItemRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Order item with id: " + dto.getId() + " not found"));
        customerOrderItem.setDescription(dto.getDescription());
        customerOrderItem.setTotalPrice(dto.getTotalPrice());
        customerOrderItem = customerOrderItemRepository.save(customerOrderItem);
        CustomerOrder customerOrder = customerOrderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found"));
        List<CustomerOrderItem> customerOrderItems = customerOrder.getOrderItems();
        for (CustomerOrderItem item : customerOrderItems) {
            if (item.getId() == dto.getId()) {
                item = customerOrderItem;
            }
        }
        customerOrder.setOrderItems(customerOrderItems);
        customerOrderRepository.save(customerOrder);
        return customerOrderItemMapper.toDto(customerOrderItem);
    }

    @Transactional
    public void deleteCustomerOrderItem(Long orderId, CustomerOrderItemDto dto) throws EntityNotFoundException {
        CustomerOrderItem customerOrderItem = customerOrderItemRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Order item with id: " + dto.getId() + " not found"));
        CustomerOrder customerOrder = customerOrderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found"));
        List<CustomerOrderItem> customerOrderItems = customerOrder.getOrderItems();
        for (CustomerOrderItem item : customerOrderItems) {
            if (item.getId() == dto.getId()) {
                item = null;
            }
        }
        customerOrder.setOrderItems(customerOrderItems);
        customerOrderRepository.save(customerOrder);
        customerOrderItemRepository.delete(customerOrderItem);
    }
}
