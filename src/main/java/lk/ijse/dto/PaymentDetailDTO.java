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
    private Date pickUpDate;
    private String cusId;
    private String name;
    private String contact;
    private String address;
}
