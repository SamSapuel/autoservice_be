package cz.cvut.fel.autoserviceIS.model;

import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Customer extends AbstractUser{
    @Basic
    @Column(name = "info")
    private String info;

}
