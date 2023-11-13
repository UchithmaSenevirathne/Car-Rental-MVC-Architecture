package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.BookDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class MakeBookingModel {
    private final BookingModel bookingModel = new BookingModel();
    private final CarModel carModel = new CarModel();
    private final DriverModel driverModel = new DriverModel();
    private final BookingDetailModel bookingDetailModel = new BookingDetailModel();

    public boolean makeBooking(BookDTO bookDto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isBookingSaved = BookingModel.saveBooking(bookDto.getBId(), bookDto.getPickUpDate(), bookDto.getDays(), bookDto.getStatus(), bookDto.getPayment(), bookDto.getCusId());
            System.out.println("booking "+ isBookingSaved);
            if (isBookingSaved) {
                boolean isCarUpdated = carModel.updateCar(bookDto.getCarList());
                if(isCarUpdated) {
                    boolean isDriverUpdated = driverModel.updateDriver(bookDto.getDriverList());
                    if(isDriverUpdated) {
                        boolean isBookingDetailSaved = bookingDetailModel.saveBookingDetail(bookDto.getBId(), bookDto.getBookList());
                        if (isBookingDetailSaved) {
                            connection.commit();
                            result = true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }
}
