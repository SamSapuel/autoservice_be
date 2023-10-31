package cz.cvut.fel.autoserviceIS.service;

import cz.cvut.fel.autoserviceIS.dto.EmployeeOrderDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.EmployeeOrderMapper;
import cz.cvut.fel.autoserviceIS.mapper.ItemMapper;
import cz.cvut.fel.autoserviceIS.model.Employee;
import cz.cvut.fel.autoserviceIS.model.EmployeeOrder;
import cz.cvut.fel.autoserviceIS.model.EmployeeOrderItem;
import cz.cvut.fel.autoserviceIS.model.Item;
import cz.cvut.fel.autoserviceIS.repository.EmployeeOrderItemRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeeOrderRepository;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeOrderService {

    private final EmployeeOrderRepository employeeOrderRepository;
    private final EmployeeOrderItemRepository employeeOrderItemRepository;
    private final ItemService itemService;
    private final EmployeeOrderMapper employeeOrderMapper;
    private final ItemMapper itemMapper;

    @Transactional
    public List<EmployeeOrderDto> findAll() {
        List<EmployeeOrder> orders = employeeOrderRepository.findAll();
        return orders.stream()
                .map(employeeOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeOrderDto getById(Long id) {
        EmployeeOrder employeeOrder = employeeOrderRepository.findById(id).orElseThrow();
        return employeeOrderMapper.toDto(employeeOrder);
    }

    @Transactional
    public List<EmployeeOrderDto> getCurrentEmployeeOrders(UserDetailsImpl userDetails) {
        List<EmployeeOrder> employeeOrders = employeeOrderRepository.findEmployeeOrderByEmployee_Id(userDetails.getUser().getId());
        return employeeOrders.stream()
                .map(employeeOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeOrderDto createOrder(EmployeeOrderDto dto, UserDetailsImpl userDetails) throws EntityNotFoundException {
        List<EmployeeOrderItem> employeeOrderItems = dto.getOrderItems();
        List<EmployeeOrderItem> employeeOrderItemsSaved = new ArrayList<>();
        for (EmployeeOrderItem orderItem : employeeOrderItems) {
            Item item = itemMapper.toEntity(itemService.findById(orderItem.getItem().getId()));
            orderItem.setItem(item);
            employeeOrderItemsSaved.add(employeeOrderItemRepository.save(orderItem));
        }
        EmployeeOrder employeeOrder = employeeOrderMapper.toEntity(dto);
        employeeOrder.setEmployee((Employee) userDetails.getUser());
        employeeOrder.setOrderItems(employeeOrderItemsSaved);
        EmployeeOrder res = employeeOrderRepository.save(employeeOrder);
        return employeeOrderMapper.toDto(res);
    }

    @Transactional
    public void deleteOrder(EmployeeOrderDto dto) throws EntityNotFoundException {
        EmployeeOrder employeeOrder = employeeOrderRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Order with id: " + dto.getId() + " not found"));
        List<EmployeeOrderItem> employeeOrderItems = dto.getOrderItems();
        for (EmployeeOrderItem orderItem : employeeOrderItems) {
            orderItem.setItem(null);
            employeeOrderItemRepository.delete(orderItem);
        }
        employeeOrder.setEmployee(null);
        employeeOrderRepository.delete(employeeOrder);
    }
}
