package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CarTm {
    private String carNo;
    private String brand;
    private Button updateButton;
    private Button deleteButton;
}
