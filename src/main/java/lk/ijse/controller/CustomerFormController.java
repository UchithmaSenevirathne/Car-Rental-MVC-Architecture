package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.model.CustomerModel;

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
    private TextField txtcusId;

    @FXML
    private TextField txtcusName;

    @FXML
    public Button btnCusFormBtn;

    Stage stage;

    private final ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

    @FXML
    void btnCancelCusOnAction(ActionEvent event) {
        stage = (Stage) rootNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnSaveCusOnAction(ActionEvent event) {
        String id = txtcusId.getText();
        String name = txtcusName.getText();
        String address = txtcusAddress.getText();
        String email = txtcusEmail.getText();
        String contact = txtcusContact.getText();

        var dto = new CustomerDto(id, name, address, email, contact);

        var model = new CustomerModel();

//        if (btnID.getText().equals("UPDATE")){
//            boolean isUpdate=model.updateCustomer(dto)
//              return;
//        }

        try {
            if(btnCusFormBtn.getText().equals("UPDATE")){
                boolean isUpdated = model.updateCustomer(dto);
                if(isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                    clearFields();
                }
            }else if(btnCusFormBtn.getText().equals("Save")) {
                boolean isSaved = model.saveCustomer(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                    clearFields();
                }
            }
        }catch (Exception e){
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

    public void setCustomerData(String id, String name, String address, String email, String contact){
        txtcusId.setText(id);
        txtcusName.setText(name);
        txtcusAddress.setText(address);
        txtcusEmail.setText(email);
        txtcusContact.setText(contact);
    }
}
