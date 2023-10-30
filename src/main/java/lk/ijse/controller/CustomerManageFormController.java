package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.tm.CustomerTm;

import java.io.IOException;

public class CustomerManageFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableColumn<CustomerTm, String> colAddress;

    @FXML
    private TableColumn<CustomerTm, String> colContact;

    @FXML
    private TableColumn<CustomerTm, String> colEmail;

    @FXML
    private TableColumn<CustomerTm, String> colId;

    @FXML
    private TableColumn<CustomerTm, String> colName;

    @FXML
    private TableView<CustomerTm> tableView;

    public void initialize(){
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private void loadAllCustomers(){

    }

    // @FXML
   /* void btnDashboardOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Dashboard Form");
        stage.setScene(scene);
    }*/

    @FXML
    void btnADDCusOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/AddCustomerForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Customer Form");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/AddCustomerForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Customer Form");
        stage.centerOnScreen();
        stage.show();
    }
}
