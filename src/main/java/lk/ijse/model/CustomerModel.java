package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;

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
}