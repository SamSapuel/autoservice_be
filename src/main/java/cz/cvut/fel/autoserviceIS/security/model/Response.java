package cz.cvut.fel.autoserviceIS.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private Boolean success;
    private String message;
}