package cz.cvut.fel.autoserviceIS.repository;

import cz.cvut.fel.autoserviceIS.model.EmployeeOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeOrderItemRepository extends JpaRepository<EmployeeOrderItem, Long> {
}
