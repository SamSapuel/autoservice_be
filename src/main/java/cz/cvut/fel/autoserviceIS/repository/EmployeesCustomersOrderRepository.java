package cz.cvut.fel.autoserviceIS.repository;

import cz.cvut.fel.autoserviceIS.model.EmployeesCustomersOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeesCustomersOrderRepository extends JpaRepository<EmployeesCustomersOrder, Long> {
    List<EmployeesCustomersOrder> findEmployeesCustomersOrderByEmployee_Id(Long id);

    EmployeesCustomersOrder findEmployeesCustomersOrderByOrder_Id(Long id);
}
