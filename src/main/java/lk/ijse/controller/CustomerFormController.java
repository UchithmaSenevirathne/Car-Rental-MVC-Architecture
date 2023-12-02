package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.model.CustomerModel;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtcusAddress;

    @FXML
    private TextField txtcusContact;

    @FXML
    private TextField txtcusEmail;

    @FXML
    public TextField txtcusId;

    @FXML
    private TextField txtcusName;

    @FXML
    public Button btnCusFormBtn;

    Stage stage;

    @FXML
    void btnCancelCusOnAction(ActionEvent event) {
        stage = (Stage) rootNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnSaveCusOnAction(ActionEvent event) throws SQLException {
        String id = txtcusId.getText();
        String name = txtcusName.getText();
        String address = txtcusAddress.getText();
        String email = txtcusEmail.getText();
        String contact = txtcusContact.getText();

        var dto = new CustomerDto(id, name, address, email, contact);

        var model = new CustomerModel();

        try {
            if(validateCustomer(id, name, address, email, contact)) {
                if (btnCusFormBtn.getText().equals("UPDATE")) {
                    boolean isUpdated = model.updateCustomer(dto);
                    if (isUpdated) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                        Parent rootNode = loader.load();

                        ConfirmationController confirmationController = loader.getController();

                        confirmationController.lblConfirm.setText("Customer updated successfully");

                        Scene scene = new Scene(rootNode);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.centerOnScreen();
                        stage.show();
                        clearFields();

                        btnCancelCusOnAction(event);
                    }
                } else if (btnCusFormBtn.getText().equals("SAVE")) {
                    boolean isSaved = model.saveCustomer(dto);
                    if (isSaved) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                        Parent rootNode = loader.load();

                        ConfirmationController confirmationController = loader.getController();

                        confirmationController.lblConfirm.setText("Customer saved successfully");

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

    private boolean validateCustomer(String id, String name, String address, String email, String contact) {
        if(!Validate.validation(id,txtcusId,"[C][0-9]{3,}")){
            return false;
        }

        if(!Validate.validation(name,txtcusName,"[A-Z][a-z]+ [A-Z][a-z]+")){
            return false;
        }

        if(!Validate.validation(address,txtcusAddress,"([a-zA-Z_\\\\s]+)")){
            return false;
        }

        if(!Validate.validation(email,txtcusEmail,"[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")){
            return false;
        }

        if(!Validate.validation(contact,txtcusContact,"[0-9]{10}")){
            return false;
        }

        return true;
    }

    private void clearFields() {
        txtcusId.setText("");
        txtcusName.setText("");
        txtcusAddress.setText("");
        txtcusEmail.setText("");
        txtcusContact.setText("");
    }

    public void setCustomerData(String id, String name, String address, String email, String contact){
        txtcusId.setText(id);
        txtcusName.setText(name);
        txtcusAddress.setText(address);
        txtcusEmail.setText(email);
        txtcusContact.setText(contact);
    }

    public void setData(String id) {
        txtcusId.setText(id);
    }
}
