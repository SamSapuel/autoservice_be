package cz.cvut.fel.autoserviceIS.repository;

import cz.cvut.fel.autoserviceIS.model.CustomerCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerCarRepository extends JpaRepository<CustomerCar, Long> {
    List<CustomerCar> findCustomerCarByCustomer_Id(Long id);
}
