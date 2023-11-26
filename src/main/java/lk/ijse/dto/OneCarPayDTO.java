package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OneCarPayDTO {
    private String bId;
    private String carNo;
    private double extraKm;
    private double driverCost;
    private double subTotal;
}
