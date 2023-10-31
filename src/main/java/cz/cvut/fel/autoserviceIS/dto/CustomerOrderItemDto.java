package cz.cvut.fel.autoserviceIS.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerOrderItemDto {
    private long id;
    private String description;
    private int totalPrice;
}
