package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookTm {
    private String bId;
    private String carNo;
    private String brand;
    private String drId;
    private String cusId;
    private String pickUpDate;
    private int days;
}
