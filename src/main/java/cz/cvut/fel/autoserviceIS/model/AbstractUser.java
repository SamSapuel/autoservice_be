package cz.cvut.fel.autoserviceIS.model;

import cz.cvut.fel.autoserviceIS.model.enums.AccessType;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "access_level")
    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    @Column(name = "password")
    private String password;


}
