package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.model.CustomerModel;

public class DeleteCustomerFormController {

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
    private TableView<CustomerTm> tableView;

    Stage stage;

    @FXML
    void btnCancelCusOnAction(ActionEvent event) {
        stage = (Stage) rootNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnDeleteCusOnAction(ActionEvent event) {
        String id = txtcusId.getText();
        
        var model = new CustomerModel();

        try {
            boolean isDeleted = model.deleteCustomer(id);

            if (isDeleted) {
                //tableView.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                clearFields();
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

    @FXML
    void txtIdOnAction(ActionEvent event) {
        String id = txtcusId.getText();

        var model = new CustomerModel();

        try{
            CustomerDto dto  = model.searchCustomer(id);

            if (dto != null) {
                fillfields(dto);
            }else{
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillfields(CustomerDto dto) {
        txtcusId.setText(dto.getId());
        txtcusName.setText(dto.getName());
        txtcusAddress.setText(dto.getAddress());
        txtcusEmail.setText(dto.getEmail());
        txtcusContact.setText(dto.getContact());
    }
}
