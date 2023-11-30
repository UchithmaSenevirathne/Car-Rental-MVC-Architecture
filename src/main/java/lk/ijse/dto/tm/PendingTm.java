package lk.ijse.dto.tm;


import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PendingTm {
    private String bId;
    private String cusId;
    private String drId;
    private String carNo;
    private String pickUpDate;
    private int days;
    private double payment;
    private Button updateButton;
    private Button deleteButton;
}
