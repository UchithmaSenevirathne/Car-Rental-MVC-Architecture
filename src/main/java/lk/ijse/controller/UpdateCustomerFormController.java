package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.CustomerDto;
import lk.ijse.model.CustomerModel;

import java.sql.SQLException;

public class UpdateCustomerFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtcusAddress;

    @FXML
    private TextField txtcusContact;

    @FXML
    private TextField txtcusEmail;

    @FXML
    private TextField txtcusId;

    @FXML
    private TextField txtcusName;

    Stage stage;

    @FXML
    void btnCancelCusOnAction(ActionEvent event) {
        stage = (Stage) rootNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnUpdateCusOnAction(ActionEvent event) {
        String id = txtcusId.getText();
        String name = txtcusName.getText();
        String address = txtcusAddress.getText();
        String email = txtcusEmail.getText();
        String contact = txtcusContact.getText();

        var dto = new CustomerDto(id, name, address, email, contact);

        var model = new CustomerModel();

        try {
            boolean isUpdated = model.updateCustomer(dto);
            //System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtcusId.setText("");
        txtcusName.setText("");
        txtcusAddress.setText("");
        txtcusEmail.setText("");
        txtcusContact.setText("");
    }

    @FXML
    void txtIdOnAction(ActionEvent event) {
        String id = txtcusId.getText();

        var model = new CustomerModel();

        try {
            CustomerDto dto = model.searchCustomerToUpdate(id);

            if(dto != null){
                fillFields(dto);
            }else{
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    private void fillFields(CustomerDto dto) {
        txtcusId.setText(dto.getId());
        txtcusName.setText(dto.getName());
        txtcusAddress.setText(dto.getAddress());
        txtcusEmail.setText(dto.getEmail());
        txtcusContact.setText(dto.getContact());
    }
}
