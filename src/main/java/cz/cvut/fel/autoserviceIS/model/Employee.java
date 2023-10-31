package cz.cvut.fel.autoserviceIS.model;

import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
