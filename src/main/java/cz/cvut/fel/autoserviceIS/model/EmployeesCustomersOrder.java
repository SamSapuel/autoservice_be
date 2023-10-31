package cz.cvut.fel.autoserviceIS.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "employees_customers_order")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class EmployeesCustomersOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
