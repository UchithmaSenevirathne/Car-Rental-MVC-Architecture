package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BookingDetailDTO;
import lk.ijse.dto.PaymentDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingDetailModel {
    public static BookingDetailDTO searchbookingDetail(String bId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM bookingdetail WHERE bId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, bId);

        ResultSet resultSet = pstm.executeQuery();

        BookingDetailDTO dto = null;

        if(resultSet.next()) {
            String b_Id = resultSet.getString(1);
            String car_Id = resultSet.getString(2);
            String dr_Id = resultSet.getString(3);

            dto = new BookingDetailDTO(b_Id,car_Id,dr_Id);
        }

        return dto;
    }
    /*public boolean saveBookingDetail(String bId, List<BookTm> bookList) throws SQLException {
        for (BookTm bookTm : bookList) {
            if(!saveBookingDetail(bId, bookTm)) {
                return false;
            }
        }
        return true;
    }*/

    public boolean saveBookingDetail(BookingDetailDTO bookingDetail) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO bookingDetail VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, bookingDetail.getBId());
        pstm.setString(2, bookingDetail.getCarNo());
        pstm.setString(3, bookingDetail.getDriverId());

        return pstm.executeUpdate() > 0;
    }

    public List<BookingDetailDTO> getAllBookings() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM bookingdetail";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<BookingDetailDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String book_Id = resultSet.getString(1);
            String car_No = resultSet.getString(2);
            String dr_Id = resultSet.getString(3);

            var dto = new BookingDetailDTO(book_Id,car_No,dr_Id);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
