package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.UserDTO;
import lk.ijse.model.DriverModel;

import java.sql.SQLException;

public class DriverFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtDrAddress;

    @FXML
    private TextField txtDrContact;

    @FXML
    private TextField txtDrEmail;

    @FXML
    private TextField txtDrId;

    @FXML
    private TextField txtDrLicenseNo;

    @FXML
    private TextField txtDrName;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPassword;

    Stage stage;

    @FXML
    void btnCancelDrOnAction(ActionEvent event) {
        stage = (Stage) rootNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnSaveDrOnAction(ActionEvent event) throws SQLException {
        String id = txtDrId.getText();
        String name = txtDrName.getText();
        String address = txtDrAddress.getText();
        String email = txtDrEmail.getText();
        String contact = txtDrContact.getText();
        String licenseNo = txtDrLicenseNo.getText();
        String userName = txtUserName.getText();
        String pwd = txtPassword.getText();

        var driverDto = new DriverDto(id, name, address, email, contact, licenseNo, userName);
        var userDto = new UserDTO(userName, pwd, "DRI");

        var model = new DriverModel();

        try {
            boolean isSaved = model.saveDriver(driverDto, userDto);

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "driver saved!").show();
                clearFields();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtDrId.setText("");
        txtDrName.setText("");
        txtDrAddress.setText("");
        txtDrEmail.setText("");
        txtDrContact.setText("");
        txtDrLicenseNo.setText("");
    }
}
