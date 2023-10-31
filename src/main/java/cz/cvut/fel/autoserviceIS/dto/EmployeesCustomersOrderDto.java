package cz.cvut.fel.autoserviceIS.dto;

import cz.cvut.fel.autoserviceIS.model.CustomerOrder;
import cz.cvut.fel.autoserviceIS.model.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeesCustomersOrderDto {
    private Long id;
    private CustomerOrder order;
    private Employee employee;
}
