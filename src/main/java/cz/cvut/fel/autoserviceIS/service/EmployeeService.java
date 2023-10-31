package cz.cvut.fel.autoserviceIS.service;

import cz.cvut.fel.autoserviceIS.dto.CustomerDto;
import cz.cvut.fel.autoserviceIS.dto.EmployeeDto;
import cz.cvut.fel.autoserviceIS.dto.EmployeeOrderDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.EmployeeMapper;
import cz.cvut.fel.autoserviceIS.model.*;
import cz.cvut.fel.autoserviceIS.repository.EmployeeOrderItemRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeeOrderRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeeRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeesCustomersOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeeOrderRepository employeeOrderRepository;
    private final EmployeeOrderItemRepository employeeOrderItemRepository;
    private final EmployeesCustomersOrderRepository employeesCustomersOrderRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public List<EmployeeDto> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeDto findById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        EmployeeDto res = employeeMapper.toDto(employee);
        return res;
    }

    @Transactional
    public EmployeeDto findByEmail(String email) {
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if (employee != null) {
            EmployeeDto res = employeeMapper.toDto(employee);
            return res;
        }
        return null;
    }

    @Transactional
    public EmployeeDto findByUsername(String username) {
        Employee employee = employeeRepository.findEmployeeByUsername(username);
        if (employee != null) {
            EmployeeDto res = employeeMapper.toDto(employee);
            return res;
        }
        return null;
    }

    @Transactional
    public EmployeeDto addEmployee(EmployeeDto dto) {
        Employee employee = employeeMapper.toEntity(dto);
        employee.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Transactional
    public EmployeeDto updateEmployee(EmployeeDto dto) throws EntityNotFoundException {
        Employee employee = employeeRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Employee with id: " + dto.getId() + " not found"));
        employee.setFirstName(dto.getFirstName());
        employee.setSecondName(dto.getSecondName());
        employee.setUsername(dto.getUsername());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setSpecialization(dto.getSpecialization());
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Transactional
    public void deleteEmployee(EmployeeDto dto) throws EntityNotFoundException {
        Employee employee = employeeRepository.findById(dto.getId()).orElseThrow(() ->
                new EntityNotFoundException("Employee with id: " + dto.getId() + " not found"));
        List<EmployeeOrder> orders = employeeOrderRepository.findEmployeeOrderByEmployee_Id(employee.getId());
        orders.stream().filter(order -> order.getEmployee().getId() == employee.getId())
                .forEach(order -> {
                    order.setEmployee(null);
                    List<EmployeeOrderItem> employeeOrderItems = order.getOrderItems();
                    order.setOrderItems(Collections.emptyList());
                    employeeOrderItems.forEach(orderItem -> {
                        orderItem.setItem(null);
                        employeeOrderItemRepository.delete(orderItem);
                    });
                    employeeOrderRepository.delete(order);
                });
        List<EmployeesCustomersOrder> employeesCustomersOrders =
                employeesCustomersOrderRepository.findEmployeesCustomersOrderByEmployee_Id(employee.getId());
        employeesCustomersOrders.forEach(element -> {
            if (element!=null) {
                element.setOrder(null);
                element.setEmployee(null);
                employeesCustomersOrderRepository.delete(element);
            }
        });
        employeeRepository.delete(employee);
    }
}
