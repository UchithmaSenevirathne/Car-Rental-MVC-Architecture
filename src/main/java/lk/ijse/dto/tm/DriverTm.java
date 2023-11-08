package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class DriverTm {
    private String id;
    private String name;
    private String address;
    private String email;
    private String contact;
    private String licenseNo;
}
