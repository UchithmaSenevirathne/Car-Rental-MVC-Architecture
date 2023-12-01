package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.BookingDetailDTO;
import lk.ijse.dto.CompleteDTO;
import lk.ijse.dto.PendingDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

            if(id==10){
                return "B0" + id;
            }else if(id == 100){
                return "B" + id;
            }
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

    public List<PendingDTO> getAllPendings() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select b.bId,b.cusId,b.pickUpDate,b.days,b.payment,bd.drId,bd.carNo from bookingDetail bd left join booking b on b.bId = bd.bId where status = 'Pending'";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<PendingDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String rent_id = resultSet.getString(1);
            String cus_id = resultSet.getString(2);
            String pickUp_date = resultSet.getString(3);
            int days = resultSet.getInt(4);
            double advance = resultSet.getDouble(5);
            String dr_id = resultSet.getString(6);
            String car_no = resultSet.getString(7);

            var dto = new PendingDTO(rent_id,cus_id,dr_id,car_no,pickUp_date,days,advance);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean deleteBooking(String bId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm1 = connection.prepareStatement("DELETE FROM bookingdetail WHERE bId = ?");

        pstm1.setString(1, bId);

        if(pstm1.executeUpdate() > 0){
            PreparedStatement pstm2 = connection.prepareStatement("DELETE FROM booking WHERE bId = ?");

            pstm2.setString(1, bId);

            return pstm2.executeUpdate() > 0;
        }

        return false;
    }

    public boolean updatePendingBooking(PendingDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm1 = connection.prepareStatement("UPDATE bookingdetail SET carNo = ?, drId = ? WHERE bId = ?");

        pstm1.setString(1, dto.getCarNo());
        pstm1.setString(2, dto.getDrId());
        pstm1.setString(3, dto.getBId());

        if(pstm1.executeUpdate() > 0){
            PreparedStatement pstm2 = connection.prepareStatement("UPDATE booking SET pickUpDate = ?, days = ?, payment = ?, cusId = ? WHERE bId = ?");

            pstm2.setString(1, dto.getPickUpDate());
            pstm2.setInt(2, dto.getDays());
            pstm2.setDouble(3, dto.getPayment());
            pstm2.setString(4, dto.getCusId());
            pstm2.setString(5, dto.getBId());

            return pstm2.executeUpdate() > 0;
        }

        return false;
    }

   public List<CompleteDTO> getAllCompletes() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select b.bId,b.cusId,b.pickUpDate,b.days,p.totalPayment from booking b join payment p on b.bId = p.bId where b.status = 'PAID'";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<CompleteDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String rent_id = resultSet.getString(1);
            String cus_id = resultSet.getString(2);
            String pickUp_date = resultSet.getString(3);
            int days = resultSet.getInt(4);
            double total = resultSet.getDouble(5);

            var dto = new CompleteDTO(rent_id,cus_id,pickUp_date,days,total);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public int getCountBooking() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select count(bId) from booking";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int count = 0;

        while (resultSet.next()){
            count = resultSet.getInt(1);
        }
        return count;
    }
}
