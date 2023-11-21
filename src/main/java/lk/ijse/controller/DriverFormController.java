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
        var userDto = new UserDTO(userName, pwd, "DRI");

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
        if(!Pattern.matches("[D][0-9]{3,}", id)){
            //new Alert(Alert.AlertType.ERROR, "Invalid Driver ID").show();
            txtDrId.setStyle("-fx-border-color: #b30404");
            return false;
        }
        if(!Pattern.matches("[A-Z][a-z]+ [A-Z][a-z]+", name)){
            //new Alert(Alert.AlertType.ERROR, "Invalid Driver Name").show();
            txtDrName.setStyle("-fx-border-color: #b30404");
            return false;
        }
        if(!Pattern.matches("([a-zA-Z_\\\\s]+)", address)){
            //new Alert(Alert.AlertType.ERROR, "Invalid Driver Address").show();
            txtDrAddress.setStyle("-fx-border-color: #b30404");
            return false;
        }
        if(!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", email)){
            //new Alert(Alert.AlertType.ERROR, "Invalid Driver Email").show();
            txtDrEmail.setStyle("-fx-border-color: #b30404");
            return false;
        }
        if(!Pattern.matches("[0-9]{10}", contact)){
            //new Alert(Alert.AlertType.ERROR, "Invalid Driver Contact").show();
            txtDrContact.setStyle("-fx-border-color: #b30404");
            return false;
        }
        if(!Pattern.matches("[A-Z](?:\\d[- ]*){12}", licenseNo)){        //L123456789012
            //new Alert(Alert.AlertType.ERROR, "Invalid Driver License Number").show();
            txtDrLicenseNo.setStyle("-fx-border-color: #b30404");
            return false;
        }
        if(!Pattern.matches("[A-Za-zA-Z]+", userName)){
            //new Alert(Alert.AlertType.ERROR, "Invalid Driver UserName").show();
            txtUserName.setStyle("-fx-border-color: #b30404");
            return false;
        }
        if(!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}", pwd)){    //#001DRpw
            //new Alert(Alert.AlertType.ERROR, "Invalid Driver Password").show();
            txtPassword.setStyle("-fx-border-color: #b30404");
            return false;
        }
        txtDrId.setStyle("-fx-border-color: default");
        txtDrName.setStyle("-fx-border-color: default");
        txtDrAddress.setStyle("-fx-border-color: default");
        txtDrEmail.setStyle("-fx-border-color: default");
        txtDrContact.setStyle("-fx-border-color: default");
        txtDrLicenseNo.setStyle("-fx-border-color: default");
        txtUserName.setStyle("-fx-border-color: default");
        txtPassword.setStyle("-fx-border-color: default");
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
