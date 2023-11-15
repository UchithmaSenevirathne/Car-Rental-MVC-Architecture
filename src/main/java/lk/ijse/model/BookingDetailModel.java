package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.bookingDetailDTO;
import lk.ijse.dto.tm.BookTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BookingDetailModel {
    /*public boolean saveBookingDetail(String bId, List<BookTm> bookList) throws SQLException {
        for (BookTm bookTm : bookList) {
            if(!saveBookingDetail(bId, bookTm)) {
                return false;
            }
        }
        return true;
    }*/

    public boolean saveBookingDetail(bookingDetailDTO bookingDetail) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO bookingDetail VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, bookingDetail.getBId());
        pstm.setString(2, bookingDetail.getCarNo());
        pstm.setString(3, bookingDetail.getDriverId());

        return pstm.executeUpdate() > 0;
    }
}
