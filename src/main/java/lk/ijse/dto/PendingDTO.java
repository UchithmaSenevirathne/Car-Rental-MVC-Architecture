package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PendingDTO {
    private String bId;
    private String cusId;
    private String drId;
    private String carNo;
    private String pickUpDate;
    private int days;
    private double payment;
}
