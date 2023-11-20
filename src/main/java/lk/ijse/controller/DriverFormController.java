package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    public TextField txtPassword;

    @FXML
    public Button btnDrFormBtn;

    Stage stage;

    @FXML
    void btnCancelDrOnAction(ActionEvent event) {
        stage = (Stage) rootNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String id = txtDrId.getText();
        String name = txtDrName.getText();
        String address = txtDrAddress.getText();
        String email = txtDrEmail.getText();
        String contact = txtDrContact.getText();
        String licenseNo = txtDrLicenseNo.getText();
        String userName = txtUserName.getText();
        String pwd = txtPassword.getText();
        String availability = "YES";

        var driverDto = new DriverDto(id, name, address, email, contact, licenseNo, userName, availability);
        var userDto = new UserDTO(userName, pwd, "DRI");

        var model = new DriverModel();

        try {
            if(btnDrFormBtn.getText().equals("UPDATE")) {
                boolean isUpdate = model.updateDriver(driverDto, userDto);
                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION, "driver updated!!").show();
                    clearFields();
                }
            }else if(btnDrFormBtn.getText().equals("Save")) {
                boolean isSaved = model.saveDriver(driverDto, userDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "driver saved!").show();
                    clearFields();
                }
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
        txtUserName.setText("");
        txtPassword.setText("");
    }


    public void setDriverData(String id, String name, String address, String email, String contact, String licenseNo, String userName) {
        txtDrId.setText(id);
        txtDrName.setText(name);
        txtDrAddress.setText(address);
        txtDrEmail.setText(email);
        txtDrContact.setText(contact);
        txtDrLicenseNo.setText(licenseNo);
        txtUserName.setText(userName);
    }
}
