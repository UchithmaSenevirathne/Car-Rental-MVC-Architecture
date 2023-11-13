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
    private String availability;
    private double currentMileage;
    private double kmOneDay;
    private double priceOneDay;
    private double priceExtraKm;
    private Button updateButton;
    private Button deleteButton;
}
