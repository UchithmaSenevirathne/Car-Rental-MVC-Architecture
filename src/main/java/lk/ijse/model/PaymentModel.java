package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.PaymentDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentModel {

    public static List<PaymentDetailDTO> searchPaymentDetail(String bId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
/*private String bId;
    private Date pickUpDate;
    private int days;
    private String status;
    private double payment;
    private String cusId;
    private String carNo;
    private String drId;*/

        String sql = "SELECT b.bId, b.pickUpDate, b.days, b.status, b.payment, b.cusId, bd.carNo, bd.drId FROM booking b LEFT JOIN bookingdetail bd ON b.bId = bd.bId WHERE bd.bId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, bId);

        List<PaymentDetailDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();


        /*
    private String cusId;
    private String carNo;
    private String drId;*/

        while (resultSet.next()) {
            String b_Id = resultSet.getString(1);
            Date date = resultSet.getDate(2);
            int rent_days = resultSet.getInt(3);
            String rent_status = resultSet.getString(4);
            double rent_pay = resultSet.getDouble(5);
            String cus_Id = resultSet.getString(6);
            String car_Id = resultSet.getString(7);
            String driverId = resultSet.getString(8);

            var dto = new PaymentDetailDTO(b_Id,date,rent_days,rent_status,rent_pay,cus_Id,car_Id,driverId);
            dtoList.add(dto);
        }

        return dtoList;
    }
}
