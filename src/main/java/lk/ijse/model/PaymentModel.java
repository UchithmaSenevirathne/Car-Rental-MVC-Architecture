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
import java.util.Date;
import java.util.List;

public class PaymentModel {

    public static PaymentDetailDTO searchPaymentDetail(String bId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT b.bId, b.pickUpDate, b.cusId, c.name, c.contact, c.address FROM booking b LEFT JOIN customer c ON b.cusId = c.cusId WHERE bId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, bId);

        ResultSet resultSet = pstm.executeQuery();

        PaymentDetailDTO dto = null;

        if(resultSet.next()) {
            String b_Id = resultSet.getString(1);
            Date date = resultSet.getDate(2);
            String cus_Id = resultSet.getString(3);
            String cus_name = resultSet.getString(4);
            String cus_contact = resultSet.getString(5);
            String cus_address = resultSet.getString(6);

            dto = new PaymentDetailDTO(b_Id,date,cus_Id,cus_name,cus_contact,cus_address);
        }

        return dto;
    }
}
