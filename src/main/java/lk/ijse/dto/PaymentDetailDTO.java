package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDetailDTO {
    private String bId;
    private String pickUpDate;
    private int days;
    private String status;
    private double payment;
    private String cusId;
    private String carNo;
    private String drId;
}
