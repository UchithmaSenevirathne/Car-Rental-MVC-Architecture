package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {

    public boolean saveCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getEmail());
        pstm.setString(5, dto.getContact());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public List<CustomerDto> getAllCustomers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<CustomerDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String cus_id = resultSet.getString(1);
            String cus_name = resultSet.getString(2);
            String cus_address = resultSet.getString(3);
            String cus_email = resultSet.getString(4);
            String cus_contact = resultSet.getString(5);

            var dto = new CustomerDto(cus_id, cus_name, cus_address, cus_email, cus_contact);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public CustomerDto searchCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE cusId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        CustomerDto dto = null;

        if(resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String cus_name = resultSet.getString(2);
            String cus_address = resultSet.getString(3);
            String cus_email = resultSet.getString(4);
            String cus_contact = resultSet.getString(5);

            dto = new CustomerDto(cus_id, cus_name, cus_address, cus_email, cus_contact);
        }

        return dto;
    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET name = ?, address = ?, email = ?, contact = ?  WHERE cusId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getEmail());
        pstm.setString(4, dto.getContact());
        pstm.setString(5, dto.getId());

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM customer WHERE cusId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public String generateNextCusId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT cusId FROM customer ORDER BY cusId DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentCusId = null;

        if (resultSet.next()) {
            currentCusId = resultSet.getString(1);
            return splitCusId(currentCusId);
        }
        return splitCusId(null);
    }

    private String splitCusId(String currentCusId) {
        if (currentCusId != null) {
            String[] split = currentCusId.split("C");
            int id = Integer.parseInt(split[1]);
            id++;

            if(id==10){
                return "C0" + id;
            }else if(id == 100){
                return "C" + id;
            }
            return "C00" + id;
        }
        return "C001";
    }

    public int getCountCus() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "select count(cusId) from customer";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int count = 0;

        while (resultSet.next()){
            count = resultSet.getInt(1);
        }
        return count;
    }
}
