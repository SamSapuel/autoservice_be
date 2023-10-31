package cz.cvut.fel.autoserviceIS.security.service;

import cz.cvut.fel.autoserviceIS.model.Customer;
import cz.cvut.fel.autoserviceIS.model.Employee;
import cz.cvut.fel.autoserviceIS.repository.CustomerRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeeRepository;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Customer customer = customerRepository.findCustomerByUsername(username);
        if (customer != null) {
            return new UserDetailsImpl(customer);
        }
        final Employee employee = employeeRepository.findEmployeeByUsername(username);
        if (employee != null) {
            return new UserDetailsImpl(employee);
        }

        throw new UsernameNotFoundException("User with username " + username + " not found.");
    }
}
