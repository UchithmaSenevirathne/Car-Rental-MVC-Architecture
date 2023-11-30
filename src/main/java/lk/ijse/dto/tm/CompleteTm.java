package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CompleteTm {
    private String bId;
    private String cusId;
    private String pickUpDate;
    private int days;
    private double totalPayment;
}
