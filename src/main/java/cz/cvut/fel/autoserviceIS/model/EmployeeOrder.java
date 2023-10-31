package cz.cvut.fel.autoserviceIS.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employee_order", schema = "public", catalog = "postgres")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class EmployeeOrder extends Order{
    @ManyToOne
    @JoinColumn(name = "employee")
    private Employee employee;

    @OneToMany
    @JoinColumn(name = "employee_order_item")
    private List<EmployeeOrderItem> orderItems;
}
