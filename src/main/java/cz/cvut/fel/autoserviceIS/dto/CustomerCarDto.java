package cz.cvut.fel.autoserviceIS.dto;

import cz.cvut.fel.autoserviceIS.model.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerCarDto {
    private long id;
    private String licensePlate;
    private Customer customer;
    private int age;
    private String condition;
    private String model;
}
