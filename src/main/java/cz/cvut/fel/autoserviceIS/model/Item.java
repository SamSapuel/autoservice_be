package cz.cvut.fel.autoserviceIS.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "in_stock", nullable = false)
    private boolean inStock;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;
}
