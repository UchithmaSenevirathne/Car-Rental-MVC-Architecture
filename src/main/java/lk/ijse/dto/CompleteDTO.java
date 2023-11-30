package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompleteDTO {
    private String bId;
    private String cusId;
    private String pickUpDate;
    private int days;
    private double totalPayment;
}
