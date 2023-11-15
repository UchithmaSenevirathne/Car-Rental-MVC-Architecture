package lk.ijse.dto;

import lk.ijse.dto.tm.BookTm;
import lk.ijse.dto.tm.CarTm;
import lk.ijse.dto.tm.DriverTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {
    private String bId;
    private Date pickUpDate;
    private int days;
    private String status;
    private double payment;
    private String cusId;
    private List<bookingDetailDTO> bookingList = new ArrayList<>();
}
