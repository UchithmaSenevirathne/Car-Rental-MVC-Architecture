package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.BookingDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BookingModel {
    public static String generateNextBookingId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT bId FROM booking ORDER BY bId DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentBookingId = null;

        if (resultSet.next()) {
            currentBookingId = resultSet.getString(1);
            return splitBookingId(currentBookingId);
        }
        return splitBookingId(null);
    }

    private static String splitBookingId(String currentBookingId) {
        if (currentBookingId != null) {
            String[] split = currentBookingId.split("B");
            int id = Integer.parseInt(split[1]);
            id++;
            return "B00" + id;
        }
        return "B001";
    }


    public static boolean saveBooking(String bId, String pickUpDate, int days, String status, double payment, String cusId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO booking VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, bId);
        pstm.setString(2, pickUpDate);
        pstm.setInt(3, days);
        pstm.setString(4, status);
        pstm.setDouble(5, payment);
        pstm.setString(6, cusId);

        return pstm.executeUpdate() > 0;
    }

    public boolean UpdateBooking(BookingDetailDTO bookingDetailDTO) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE booking SET status = 'PAID' WHERE bId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, bookingDetailDTO.getBId());

        return pstm.executeUpdate() > 0;
    }
}
