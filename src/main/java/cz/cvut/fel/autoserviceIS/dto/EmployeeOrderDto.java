package cz.cvut.fel.autoserviceIS.dto;

import cz.cvut.fel.autoserviceIS.model.Employee;
import cz.cvut.fel.autoserviceIS.model.EmployeeOrderItem;
import cz.cvut.fel.autoserviceIS.model.enums.OrdersStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class EmployeeOrderDto {
    private long id;
    private LocalDateTime creationTime;
    private OrdersStatus status;
    private Employee employee;
    private List<EmployeeOrderItem> orderItems;
}
