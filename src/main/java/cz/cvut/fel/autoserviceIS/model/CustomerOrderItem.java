package cz.cvut.fel.autoserviceIS.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "customer_order_item", schema = "public", catalog = "postgres")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class CustomerOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "total_price", nullable = true)
    private int totalPrice;

}
