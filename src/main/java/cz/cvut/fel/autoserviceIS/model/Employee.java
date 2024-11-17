package cz.cvut.fel.autoserviceIS.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "employee")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@Entity
public class Employee extends AbstractUser {
    @Basic
    @Column(name = "specialization")
    private String specialization;

}
