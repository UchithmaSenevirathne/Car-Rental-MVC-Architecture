package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.PendingDTO;
import lk.ijse.model.BookingModel;
import lk.ijse.model.CarModel;

import java.time.LocalDate;

public class UpdateBookingFormController {
    @FXML
    private DatePicker pickUpDate;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtAdvance;

    @FXML
    private TextField txtBId;

    @FXML
    private TextField txtCarNo;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtDrId;

    @FXML
    private TextField txtxDays;

    Stage stage;

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        stage = (Stage) rootNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String bId = txtBId.getText();
        String cusId = txtCusId.getText();
        String drId = txtDrId.getText();
        String carNo = txtCarNo.getText();
        String date = String.valueOf(pickUpDate.getValue());
        int days = Integer.parseInt(txtxDays.getText());
        double payment = Double.parseDouble(txtAdvance.getText());

        var dto = new PendingDTO(bId,cusId,drId,carNo,date,days,payment);

        var model = new BookingModel();

        try{
            boolean isUpdated = model.updatePendingBooking(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Booking updated!!").show();
                return;
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void setBookingData(String bId, String cusId, String drId, String carNo, String date, int days, double payment) {
        txtBId.setText(bId);
        txtCusId.setText(cusId);
        txtDrId.setText(drId);
        txtCarNo.setText(carNo);
        pickUpDate.setValue(LocalDate.parse(date));
        txtxDays.setText(String.valueOf(days));
        txtAdvance.setText(String.valueOf(payment));
    }
}
