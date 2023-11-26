package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.OneCarPayDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OneCarPayModel {
    public static boolean savePayment(OneCarPayDTO dto) throws SQLException {
        System.out.println("*****");
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO oneCarPayment VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getBId());
        pstm.setString(2, dto.getCarNo());
        pstm.setDouble(3, dto.getExtraKm());
        pstm.setDouble(4, dto.getDriverCost());
        pstm.setDouble(5, dto.getSubTotal());

        System.out.println(pstm.executeUpdate() > 0);
        return pstm.executeUpdate() > 0;
    }
}
