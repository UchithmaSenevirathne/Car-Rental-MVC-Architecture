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

    private final ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

    //private CustomerManageFormController customerManageFormController;

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

//        if (btnID.getText().equals("UPDATE")){
//            boolean isUpdate=model.updateCustomer(dto)
//              return;
//        }

        try {
            if(validateCustomer(id, name, address, email, contact)) {
                if (btnCusFormBtn.getText().equals("UPDATE")) {
                    boolean isUpdated = model.updateCustomer(dto);
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                        clearFields();
                    }
                } else if (btnCusFormBtn.getText().equals("Save")) {
                    boolean isSaved = model.saveCustomer(dto);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                        clearFields();
                    }
                }
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        //customerManageFormController.loadAllCustomers();
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


   /*private boolean validateCustomer(String id, String name, String address, String email, String contact) {
        if(!Pattern.matches("[C][0-9]{3,}", id)){
            txtcusId.setStyle("-fx-border-color: #b30404");
            return false;
        }
        if(!Pattern.matches("[A-Z][a-z]+ [A-Z][a-z]+", name)){
            //new Alert(Alert.AlertType.ERROR, "Invalid Customer Name").show();
            txtcusName.setStyle("-fx-border-color: #b30404");
            return false;
        }
        if(!Pattern.matches("([a-zA-Z_\\\\s]+)", address)){
            //new Alert(Alert.AlertType.ERROR, "Invalid Customer Address").show();
            txtcusAddress.setStyle("-fx-border-color: #b30404");
            return false;
        }
        if(!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", email)){
            //new Alert(Alert.AlertType.ERROR, "Invalid Customer Email").show();
            txtcusEmail.setStyle("-fx-border-color: #b30404");
            return false;
        }
        if(!Pattern.matches("[0-9]{10}", contact)){
            //new Alert(Alert.AlertType.ERROR, "Invalid Customer Contact").show();
            txtcusContact.setStyle("-fx-border-color: #b30404");
            return false;
        }
        txtcusId.setStyle("-fx-border-color: default");
        txtcusName.setStyle("-fx-border-color: default");
        txtcusAddress.setStyle("-fx-border-color: default");
        txtcusEmail.setStyle("-fx-border-color: default");
        txtcusContact.setStyle("-fx-border-color: default");
        return true;
    }*/

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
