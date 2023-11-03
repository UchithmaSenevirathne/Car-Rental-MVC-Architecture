package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.DriverDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverModel {

    public boolean saveDriver(DriverDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO driver VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getEmail());
        pstm.setString(5, dto.getContact());
        pstm.setString(6, dto.getLicenseNo());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public List<DriverDto> getAllDrivers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM driver";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<DriverDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String dr_id = resultSet.getString(1);
            String dr_name = resultSet.getString(2);
            String dr_address = resultSet.getString(3);
            String dr_email = resultSet.getString(4);
            String dr_contact = resultSet.getString(5);
            String dr_licenseNo = resultSet.getString(6);

            var dto = new DriverDto(dr_id, dr_name, dr_address, dr_email, dr_contact, dr_licenseNo);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
