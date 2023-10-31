package cz.cvut.fel.autoserviceIS.config;

import cz.cvut.fel.autoserviceIS.model.Customer;
import cz.cvut.fel.autoserviceIS.model.Employee;
import cz.cvut.fel.autoserviceIS.model.Item;
import cz.cvut.fel.autoserviceIS.model.enums.AccessType;
import cz.cvut.fel.autoserviceIS.repository.CustomerRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeeRepository;
import cz.cvut.fel.autoserviceIS.repository.ItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ItemsRepository itemsRepository;

    @Override
    public void run(String... args) throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Dmitriy");
        employee.setSecondName("Shevchenko");
        employee.setUsername("shevanister");
        employee.setPassword(new BCryptPasswordEncoder().encode("test"));
        employee.setAccessType(AccessType.EMPLOYEE_ACCESS);
        employee.setEmail("shevcdmi@fel.cvut.cz");
        employee.setPhone("+420765567876");
        employee.setSpecialization("Toyota cars");
        Customer customer = new Customer();
        customer.setFirstName("Dmitriy");
        customer.setSecondName("Shevchenko");
        customer.setUsername("shevan");
        customer.setPassword(new BCryptPasswordEncoder().encode("test"));
        customer.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer.setEmail("shev@fel.cvut.cz");
        customer.setPhone("+420765567876");
        customer.setInfo("I like cars");
        Employee admin = new Employee();
        admin.setFirstName("Dmitriy");
        admin.setSecondName("Shevchenko");
        admin.setUsername("admin");
        admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
        admin.setAccessType(AccessType.ADMIN_ACCESS);
        admin.setEmail("admin@fel.cvut.cz");
        admin.setPhone("+420765567876");
        admin.setSpecialization("Big boss");
        Item wheel = new Item();
        wheel.setName("Wheel");
        wheel.setInStock(true);
        wheel.setPrice(300);
        Item engine = new Item();
        engine.setName("EngineV8");
        engine.setInStock(true);
        engine.setPrice(1000);
        customerRepository.save(customer);
        employeeRepository.save(employee);
        employeeRepository.save(admin);
        itemsRepository.save(wheel);
        itemsRepository.save(engine);
    }
}
