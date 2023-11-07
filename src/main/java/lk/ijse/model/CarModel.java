package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarModel {
    public boolean saveCar(CarDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO car VALUES(?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCarNo());
        pstm.setString(2, dto.getBrand());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public List<CarDto> getAllCars() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM car";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<CarDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String car_number = resultSet.getString(1);
            String car_brand = resultSet.getString(2);

            var dto = new CarDto(car_number, car_brand);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean updateCar(CarDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE car SET brand = ? WHERE carNo = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getBrand());
        pstm.setString(2, dto.getCarNo());

        return pstm.executeUpdate() > 0;
    }

    public CarDto searchCar(String carNo) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM car WHERE carNo = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, carNo);

        ResultSet resultSet = pstm.executeQuery();

        CarDto dto = null;

        if(resultSet.next()) {
            String car_number = resultSet.getString(1);
            String car_brand = resultSet.getString(2);

            dto = new CarDto(car_number, car_brand);
        }

        return dto;
    }

    public boolean deleteCar(String carNo) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM car WHERE carNo = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, carNo);

        return pstm.executeUpdate() > 0;
    }
}
