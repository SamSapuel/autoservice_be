package cz.cvut.fel.autoserviceIS.repository;

import cz.cvut.fel.autoserviceIS.model.CustomerOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderItemRepository extends JpaRepository<CustomerOrderItem, Long> {
}
