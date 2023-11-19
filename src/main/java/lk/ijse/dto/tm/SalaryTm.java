package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalaryTm {
    private String drSalId;
    private String drId;
    private double amount;
    private String month;
}
