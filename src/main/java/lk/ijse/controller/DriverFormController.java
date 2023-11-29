package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Validation.Validate;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.UserDTO;
import lk.ijse.model.DriverModel;

import java.sql.SQLException;
import java.util.regex.Pattern;

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
        var userDto = new UserDTO(userName, pwd, email, "DRI");

        var model = new DriverModel();

        try {
            if(validateDriver(id, name, address, email, contact, licenseNo, userName, pwd)) {
                if (btnDrFormBtn.getText().equals("UPDATE")) {
                    boolean isUpdate = model.updateDriver(driverDto, userDto);
                    if (isUpdate) {
                        new Alert(Alert.AlertType.CONFIRMATION, "driver updated!!").show();
                        clearFields();
                    }
                } else if (btnDrFormBtn.getText().equals("Save")) {
                    boolean isSaved = model.saveDriver(driverDto, userDto);

                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "driver saved!").show();
                        clearFields();
                    }
                }
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean validateDriver(String id, String name, String address, String email, String contact, String licenseNo, String userName, String pwd) {
        if(!Validate.validation(id, txtDrId, "[D][0-9]{3,}")){
            return false;
        }
        if(!Validate.validation(name, txtDrName,"[A-Z][a-z]+ [A-Z][a-z]+")){
            return false;
        }
        if(!Validate.validation(address, txtDrAddress,"([a-zA-Z_\\\\s]+)")){
            return false;
        }
        if(!Validate.validation(email, txtDrEmail,"[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")){
            return false;
        }
        if(!Validate.validation(contact, txtDrContact,"[0-9]{10}")){
            return false;
        }
        if(!Validate.validation(licenseNo, txtDrLicenseNo,"[A-Z](?:\\d[- ]*){12}")){        //L123456789012
            return false;
        }
        if(!Validate.validation(userName, txtUserName,"[A-Za-zA-Z]+")){
            return false;
        }
        if(!Validate.validation(pwd, txtPassword,"(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}")){    //#001DRpw
            return false;
        }
        return true;
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
