package cz.cvut.fel.autoserviceIS.repository;

import cz.cvut.fel.autoserviceIS.model.EmployeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeOrderRepository extends JpaRepository<EmployeeOrder, Long> {
    List<EmployeeOrder> findEmployeeOrderByEmployee_Id(Long id);
}
