package cz.cvut.fel.autoserviceIS.service;

import cz.cvut.fel.autoserviceIS.dto.CustomerCarDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.CustomerCarMapper;
import cz.cvut.fel.autoserviceIS.mapper.CustomerMapper;
import cz.cvut.fel.autoserviceIS.model.*;
import cz.cvut.fel.autoserviceIS.repository.*;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerCarService {

    private final CustomerCarRepository customerCarRepository;
    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final EmployeesCustomersOrderRepository employeesCustomersOrderRepository;
    private final CustomerOrderItemRepository customerOrderItemRepository;
    private final CustomerCarMapper customerCarMapper;

    @Transactional
    public List<CustomerCarDto> getAllCars() {
        List<CustomerCar> cars = customerCarRepository.findAll();
        return cars.stream()
                .map(customerCarMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerCarDto findById(Long id) throws EntityNotFoundException {
        CustomerCar customerCar = customerCarRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car with id: " + id + " not found"));
        CustomerCarDto res = customerCarMapper.toDto(customerCar);
        return res;
    }

    @Transactional
    public List<CustomerCarDto> findCustomerCars(Long id) {
        List<CustomerCar> customerCars = customerCarRepository.findCustomerCarByCustomer_Id(id);
        return customerCars.stream()
                .map(customerCarMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerCarDto createCar(CustomerCarDto dto, UserDetailsImpl userDetails) throws EntityNotFoundException {
        Assert.notNull(dto);
        CustomerCar customerCar = customerCarMapper.toEntity(dto);
        customerCar.setCustomer((Customer) userDetails.getUser());
        CustomerCar res = customerCarRepository.save(customerCar);
        return customerCarMapper.toDto(res);
    }

    @Transactional
    public void deleteCar(CustomerCarDto dto) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(dto.getCustomer().getId()).orElseThrow(() -> new EntityNotFoundException("Customer with id: " + dto.getCustomer().getId() + " not found"));
        CustomerCar customerCar = customerCarRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Car with id: " + dto.getId() + " not found"));
        List<CustomerOrder> customerOrders = customerOrderRepository.findCustomerOrderByCustomer_Id(customer.getId());
        for (CustomerOrder customerOrder : customerOrders) {
            if (customerOrder.getCustomerCar().getId() == customerCar.getId()) {
                EmployeesCustomersOrder employeesCustomersOrder = employeesCustomersOrderRepository.findEmployeesCustomersOrderByOrder_Id(customerOrder.getId());
                employeesCustomersOrderRepository.delete(employeesCustomersOrder);
                List<CustomerOrderItem> items = customerOrder.getOrderItems();
                customerOrderItemRepository.deleteAll(items);
                customerOrder.setCustomerCar(null);
                customerOrderRepository.delete(customerOrder);
            }
        }
        customerCar.setCustomer(null);
        customerCarRepository.delete(customerCar);
    }
}
