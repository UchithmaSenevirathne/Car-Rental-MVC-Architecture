package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.tm.BookTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BookingDetailModel {
    public boolean saveBookingDetail(String bId, List<BookTm> bookList) throws SQLException {
        for (BookTm bookTm : bookList) {
            if(!saveBookingDetail(bId, bookTm)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveBookingDetail(String bId, BookTm bookTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO bookingDetail VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, bId);
        pstm.setString(2, bookTm.getCarNo());
        pstm.setString(3, bookTm.getDrId());

        return pstm.executeUpdate() > 0;
    }
}
