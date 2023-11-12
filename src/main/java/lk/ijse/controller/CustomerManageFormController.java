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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colUpdate;

    @FXML
    private TableView<CustomerTm> tableView;

    private final ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

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
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
    }

    private void loadAllCustomers(){
        obList.clear();

        var model = new CustomerModel();

        //ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try{
            List<CustomerDto> dtoList = model.getAllCustomers();

            for(CustomerDto dto : dtoList){
                Button updateButton = new Button("Update");
                Button deleteButton = new Button("Delete");

                updateButton.setOnAction(event -> openCustomerPopup(dto));
                deleteButton.setOnAction(event -> deleteCustomer(dto.getId()));
                obList.add(
                        new CustomerTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getEmail(),
                                dto.getContact(),
                                updateButton,
                                deleteButton
                        )
                );
            }
            tableView.setItems(obList);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void openCustomerPopup(CustomerDto customerDto){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerForm.fxml"));

            Parent rootNode = loader.load();

            CustomerFormController customerFormController = loader.getController();

            customerFormController.btnCusFormBtn.setText("UPDATE");

            customerFormController.setCustomerData(
                    customerDto.getId(),
                    customerDto.getName(),
                    customerDto.getAddress(),
                    customerDto.getEmail(),
                    customerDto.getContact()
            );

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update Customer Form");
            stage.centerOnScreen();
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void deleteCustomer(String id){
        var model = new CustomerModel();

        try {
            boolean b = model.deleteCustomer(id);

            if(b){
                loadAllCustomers();
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

     @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Dashboard Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnDriverOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DriverManageForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Driver Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnADDCusOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Customer Form");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setScene(scene); //stage.setScene(new Scene(rootNode));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnSEARCHOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Login Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

}
