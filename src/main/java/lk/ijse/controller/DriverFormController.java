package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                        Parent rootNode = loader.load();

                        ConfirmationController confirmationController = loader.getController();

                        confirmationController.lblConfirm.setText("Driver updated successfully");

                        Scene scene = new Scene(rootNode);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.centerOnScreen();
                        stage.show();
                        clearFields();

                        btnCancelDrOnAction(event);
                    }
                } else if (btnDrFormBtn.getText().equals("SAVE")) {
                    boolean isSaved = model.saveDriver(driverDto, userDto);

                    if (isSaved) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                        Parent rootNode = loader.load();

                        ConfirmationController confirmationController = loader.getController();

                        confirmationController.lblConfirm.setText("Driver saved successfully");

                        Scene scene = new Scene(rootNode);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.centerOnScreen();
                        stage.show();
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
        if(!Validate.validation(licenseNo, txtDrLicenseNo,"[A-Z](?:\\d[- ]*){5}")){        //L12345
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


    public void setDriverData(String id, String name, String address, String email, String contact, String licenseNo, String userName, String password) {
        txtDrId.setText(id);
        txtDrName.setText(name);
        txtDrAddress.setText(address);
        txtDrEmail.setText(email);
        txtDrContact.setText(contact);
        txtDrLicenseNo.setText(licenseNo);
        txtUserName.setText(userName);
        txtPassword.setText(password);
    }

    public void setData(String drId) {
        txtDrId.setText(drId);
    }
}
