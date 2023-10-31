package cz.cvut.fel.autoserviceIS.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "customer_car", schema = "public", catalog = "postgres")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class CustomerCar {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "license_plate")
    private String licensePlate;
    @ManyToOne
    @JoinColumn(name = "owner")
    private Customer customer;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "condition", nullable = false)
    private String condition;

    @Column(name = "model", nullable = false)
    private String model;

}
