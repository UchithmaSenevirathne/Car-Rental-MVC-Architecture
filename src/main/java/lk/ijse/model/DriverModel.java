package lk.ijse.model;

import javafx.fxml.FXMLLoader;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverModel {

    public boolean saveDriver(DriverDto dto, UserDTO userDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm1 = connection.prepareStatement("INSERT INTO user VALUES(?, ?, ?)");

        pstm1.setString(1, userDto.getUserName());
        pstm1.setString(2, userDto.getPassword());
        pstm1.setString(3, userDto.getRole());

        if(pstm1.executeUpdate()>0) {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO driver VALUES(?, ?, ?, ?, ?, ?, ?)");

            pstm.setString(1, dto.getId());
            pstm.setString(2, dto.getName());
            pstm.setString(3, dto.getAddress());
            pstm.setString(4, dto.getEmail());
            pstm.setString(5, dto.getContact());
            pstm.setString(6, dto.getLicenseNo());
            pstm.setString(7, dto.getUserName());

            boolean isSaved = pstm.executeUpdate() > 0;

            return isSaved;
        }

        return false;
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
            String dr_userName = resultSet.getString(7);

            var dto = new DriverDto(dr_id, dr_name, dr_address, dr_email, dr_contact, dr_licenseNo, dr_userName);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean updateDriver(DriverDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE driver SET name = ?, address = ?, email = ?, contact = ?, licenseNo = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getEmail());
        pstm.setString(4, dto.getContact());
        pstm.setString(5, dto.getLicenseNo());
        pstm.setString(6, dto.getId());

        return pstm.executeUpdate() > 0;
    }

    public DriverDto searchDriver(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM driver WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        DriverDto dto = null;

        if (resultSet.next()) {
            String dr_id = resultSet.getString(1);
            String dr_name = resultSet.getString(2);
            String dr_address = resultSet.getString(3);
            String dr_email = resultSet.getString(4);
            String dr_contact = resultSet.getString(5);
            String dr_licenseNo = resultSet.getString(6);
            String dr_userName = resultSet.getString(7);

            dto = new DriverDto(dr_id, dr_name, dr_address, dr_email, dr_contact, dr_licenseNo, dr_userName);
        }
        return dto;
    }

    public boolean deleteDriver(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM driver WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
}
