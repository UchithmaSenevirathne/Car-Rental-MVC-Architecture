package lk.ijse.dto.tm;

import javafx.scene.control.Button;
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
    private String userName;
    private Button updateButton;
    private Button deleteButton;
}
