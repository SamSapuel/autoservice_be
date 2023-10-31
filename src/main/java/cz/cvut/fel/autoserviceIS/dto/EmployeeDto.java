package cz.cvut.fel.autoserviceIS.dto;

import cz.cvut.fel.autoserviceIS.model.enums.AccessType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EmployeeDto {
    private long id;
    private String firstName;
    private String secondName;
    private String username;
    private String email;
    private String phone;
    private AccessType accessType;
    private String password;
    private String specialization;

}
