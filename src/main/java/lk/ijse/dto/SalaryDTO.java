package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalaryDTO {
    private String drSalId;
    private String drId;
    private double amount;
    private String month;
}
