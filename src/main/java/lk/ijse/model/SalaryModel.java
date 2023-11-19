package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.SalaryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SalaryModel {
    public boolean saveSalary(SalaryDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO driverSalary VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getDrSalId());
        pstm.setString(2, dto.getDrId());
        pstm.setDouble(3, dto.getAmount());
        pstm.setString(4, dto.getMonth());

        return pstm.executeUpdate() > 0;
    }

    public List<SalaryDTO> getAllSalary() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM driverSalary";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SalaryDTO> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String sal_Id = resultSet.getString(1);
            String dr_Id = resultSet.getString(2);
            double sal_amount = resultSet.getDouble(3);
            String sal_month = resultSet.getString(4);

            var dto = new SalaryDTO(sal_Id, dr_Id, sal_amount, sal_month);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<DriverDto> getAllDrivers(String search) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = null;

        if(Pattern.matches("[C][0-9]{3,}", search)) {
            String sql = "SELECT * FROM driver WHERE drId = ? ";
            pstm = connection.prepareStatement(sql);

            pstm.setString(1, search);
        }else{
            String sql = "SELECT * FROM driver WHERE name = ? ";
            pstm = connection.prepareStatement(sql);

            pstm.setString(1, search);
        }

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
            String dr_availability = resultSet.getString(8);

            var dto = new DriverDto(dr_id, dr_name, dr_address, dr_email, dr_contact, dr_licenseNo, dr_userName, dr_availability);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
