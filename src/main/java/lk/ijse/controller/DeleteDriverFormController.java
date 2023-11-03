package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.DriverDto;
import lk.ijse.model.DriverModel;

public class DeleteDriverFormController {
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
    void btnCancelDrOnAction(ActionEvent event) {
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnDeleteDrOnAction(ActionEvent event) {
        String id = txtDrId.getText();

        var model = new DriverModel();

        try {
            boolean isDeleted = model.deleteDriver(id);

            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "driver deleted!").show();
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

    @FXML
    void txtIdOnAction(ActionEvent event) {
        String id = txtDrId.getText();

        var model = new DriverModel();

        try {
            DriverDto dto = model.searchDriver(id);

            if(dto != null){
                fillFields(dto);
            }else{
                new Alert(Alert.AlertType.INFORMATION, "driver not found!").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(DriverDto dto) {
        txtDrId.setText(dto.getId());
        txtDrName.setText(dto.getName());
        txtDrAddress.setText(dto.getAddress());
        txtDrEmail.setText(dto.getEmail());
        txtDrContact.setText(dto.getContact());
        txtDrLicenseNo.setText(dto.getLicenseNo());
    }
}
