package cz.cvut.fel.autoserviceIS.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginStatus {

    private boolean loggedIn;
    private String username;
    private String errorMessage;
    private boolean success;

}