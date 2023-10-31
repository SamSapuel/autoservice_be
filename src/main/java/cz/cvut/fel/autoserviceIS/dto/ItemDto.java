package cz.cvut.fel.autoserviceIS.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {
    private long id;
    private boolean inStock;
    private String name;
    private int price;
}
