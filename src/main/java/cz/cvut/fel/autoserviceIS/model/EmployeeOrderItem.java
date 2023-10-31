package cz.cvut.fel.autoserviceIS.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "employee_order_item", schema = "public", catalog = "postgres")
public class EmployeeOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "amount")
    private int amount;

    @ManyToOne
    @JoinColumn(name = "item")
    private Item item;

}
