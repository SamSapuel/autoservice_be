package cz.cvut.fel.autoserviceIS.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer_order", schema = "public", catalog = "postgres")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class CustomerOrder extends Order {

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "customer_car")
    private CustomerCar customerCar;

    @Column(name = "date_of_order", nullable = false)
    private LocalDateTime dateOfOrder;

    @OneToMany
    @JoinColumn(name = "order_item")
    private List<CustomerOrderItem> orderItems;

}
