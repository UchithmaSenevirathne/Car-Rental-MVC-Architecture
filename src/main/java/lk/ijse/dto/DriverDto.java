package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class DriverDto {
    private String id;
    private String name;
    private String address;
    private String email;
    private String contact;
    private String licenseNo;
    private String userName;
    private String availability;
}
