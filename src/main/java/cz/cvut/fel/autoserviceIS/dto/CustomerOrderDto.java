package cz.cvut.fel.autoserviceIS.dto;

import cz.cvut.fel.autoserviceIS.model.*;
import cz.cvut.fel.autoserviceIS.model.enums.OrdersStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CustomerOrderDto {
    private long id;
    private LocalDateTime creationTime;
    private OrdersStatus status;
    private Customer customer;
    private CustomerCar customerCar;
    private LocalDateTime dateOfOrder;
    private List<CustomerOrderItem> orderItems;
}
