package cz.cvut.fel.autoserviceIS.service;

import cz.cvut.fel.autoserviceIS.dto.CustomerDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.CustomerMapper;
import cz.cvut.fel.autoserviceIS.model.*;
import cz.cvut.fel.autoserviceIS.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerCarRepository customerCarRepository;
    private final CustomerOrderItemRepository customerOrderItemRepository;
    private final EmployeesCustomersOrderRepository employeesCustomersOrderRepository;
    private final CustomerMapper customerMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public List<CustomerDto> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerDto findById(Long id) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer with id: " + id + " not found"));
        CustomerDto res = customerMapper.toDto(customer);
        return res;
    }

    @Transactional
    public CustomerDto findByEmail(String email) {
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer != null) {
            CustomerDto res = customerMapper.toDto(customer);
            return res;
        }
        return null;
    }

    @Transactional
    public CustomerDto findByUsername(String username) {
        Customer customer = customerRepository.findCustomerByUsername(username);
        if (customer != null) {
            CustomerDto res = customerMapper.toDto(customer);
            return res;
        }
        return null;
    }

    @Transactional
    public CustomerDto addCustomer(CustomerDto dto) {
        Customer customer = customerMapper.toEntity(dto);
        customer.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Transactional
    public CustomerDto updateCustomer(CustomerDto dto) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Customer with id: " + dto.getId() + " not found"));
        customer.setFirstName(dto.getFirstName());
        customer.setSecondName(dto.getSecondName());
        customer.setUsername(dto.getUsername());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setInfo(dto.getInfo());
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Transactional
    public void deleteCustomer(CustomerDto dto) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Customer with id: " + dto.getId() + " not found"));
        List<CustomerOrder> customerOrders = customerOrderRepository.findCustomerOrderByCustomer_Id(customer.getId());
        List<CustomerCar> customerCars = customerCarRepository.findCustomerCarByCustomer_Id(customer.getId());
        for (CustomerOrder order : customerOrders) {
            EmployeesCustomersOrder employeesCustomersOrder = employeesCustomersOrderRepository.findEmployeesCustomersOrderByOrder_Id(order.getId());
            List<CustomerOrderItem> items = order.getOrderItems();
            if (employeesCustomersOrder != null) {
                employeesCustomersOrderRepository.delete(employeesCustomersOrder);
            }
            customerOrderItemRepository.deleteAll(items);
        }
        customerOrderRepository.deleteAll(customerOrders);
        customerCarRepository.deleteAll(customerCars);
        customerRepository.delete(customer);
    }
}
