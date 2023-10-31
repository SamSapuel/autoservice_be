package cz.cvut.fel.autoserviceIS.repository;

import cz.cvut.fel.autoserviceIS.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findEmployeeByEmail(String email);
    Employee findEmployeeByUsername(String username);
}
