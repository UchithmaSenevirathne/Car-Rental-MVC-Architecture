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

        String sql = "INSERT INTO car VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCarNo());
        pstm.setString(2, dto.getBrand());
        pstm.setString(3, dto.getAvailability());
        pstm.setDouble(4, dto.getCurrentMilage());
        pstm.setDouble(5, dto.getKmOneDay());
        pstm.setDouble(6, dto.getPriceOneDay());
        pstm.setDouble(7, dto.getPriceExtraKm());

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
            String car_availability = resultSet.getString(3);
            double car_milage = resultSet.getDouble(4);
            double km_day = resultSet.getDouble(5);
            double price_day = resultSet.getDouble(6);
            double price_extra = resultSet.getDouble(7);

            var dto = new CarDto(car_number, car_brand, car_availability, car_milage, km_day, price_day, price_extra);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean updateCar(CarDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE car SET brand = ?, availability = ?, currentMilage = ?, kmOneDay = ?, priceOneDay = ?, priceExtraKm = ? WHERE carNo = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getBrand());
        pstm.setString(2, dto.getAvailability());
        pstm.setDouble(3, dto.getCurrentMilage());
        pstm.setDouble(4, dto.getKmOneDay());
        pstm.setDouble(5, dto.getPriceOneDay());
        pstm.setDouble(6, dto.getPriceExtraKm());
        pstm.setString(7, dto.getCarNo());

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
            String car_availability = resultSet.getString(3);
            double car_milage = resultSet.getDouble(4);
            double km_day = resultSet.getDouble(5);
            double price_day = resultSet.getDouble(6);
            double price_extra = resultSet.getDouble(7);

            dto = new CarDto(car_number, car_brand, car_availability, car_milage, km_day, price_day, price_extra);
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
