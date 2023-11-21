package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheduleTm {
    private String bId;
    private String name;
    private String brand;
    private String address;
    private String contact;
    private String pickUpDate;
    private int days;
}
