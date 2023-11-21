package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.ScheduleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleModel {

    public List<ScheduleDTO> getSchedule(String userName) throws SQLException {
        System.out.println("++++++");
        Connection connection = DbConnection.getInstance().getConnection();

        String sql1 = "SELECT drId FROM driver WHERE userName = ?";
        PreparedStatement pstm1 = connection.prepareStatement(sql1);

        pstm1.setString(1, userName);

        ResultSet resultSet1 = pstm1.executeQuery();

        String drId = null;

        while (resultSet1.next()) {
            drId = resultSet1.getString(1);
        }
        System.out.println(drId);

        if(!drId.equals(null)) {

            String sql2 = "select bd.bId,c.name,cr.brand,c.address,c.contact,b.pickUpDate,b.days from car cr left join bookingdetail bd on cr.carNo = bd.carNo left join booking b on bd.bId = b.bId left join customer c on b.cusId = c.cusId where bd.drId = ?";
            PreparedStatement pstm2 = connection.prepareStatement(sql2);

            pstm2.setString(1, drId);

            List<ScheduleDTO> dtoList = new ArrayList<>();

            ResultSet resultSet2 = pstm2.executeQuery();

            while (resultSet2.next()) {
                String b_id = resultSet2.getString(1);
                String cus_name = resultSet2.getString(2);
                String car_brand = resultSet2.getString(3);
                String cus_address = resultSet2.getString(4);
                String cus_contact = resultSet2.getString(5);
                String pickUpDate = resultSet2.getString(6);
                Integer days = resultSet2.getInt(7);

                var dto = new ScheduleDTO(b_id, cus_name, car_brand, cus_address, cus_contact, pickUpDate, days);
                dtoList.add(dto);
            }
            return dtoList;
        }
        return null;
    }

    public String getDrName(String userName) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT name FROM driver WHERE userName = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, userName);

        ResultSet resultSet1 = pstm.executeQuery();

        String name = null;

        while (resultSet1.next()) {
            name = resultSet1.getString(1);
        }
        return name;
    }
}
