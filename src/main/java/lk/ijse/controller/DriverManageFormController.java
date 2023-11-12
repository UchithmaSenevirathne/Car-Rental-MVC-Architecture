package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.DriverTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.DriverModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DriverManageFormController {
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colAvailablity;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLicenseNo;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colUpdate;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<DriverTm> tableView;

    private final ObservableList<DriverTm> obList = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        loadAllDrivers();
    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colLicenseNo.setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("UpdateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("DeleteButton"));
    }

    private void loadAllDrivers(){
        obList.clear();

        var model = new DriverModel();

        try {
            List<DriverDto> dtoList = model.getAllDrivers();

            for(DriverDto dto : dtoList){
                Button updateButton = new Button("Update");
                Button deleteButton = new Button("Delete");

                updateButton.setOnAction(event -> openDriverPopup(dto));
                deleteButton.setOnAction(event -> deleteDriver(dto.getId()));
                obList.add(
                        new DriverTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getEmail(),
                                dto.getContact(),
                                dto.getLicenseNo(),
                                dto.getUserName(),
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

    private void deleteDriver(String id) {
        var model = new DriverModel();

        try {
            boolean b = model.deleteDriver(id);

            if(b){
                loadAllDrivers();
                new Alert(Alert.AlertType.CONFIRMATION, "driver deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void openDriverPopup(DriverDto driverDto){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DriverForm.fxml"));

            Parent rootNode = loader.load();

            DriverFormController driverFormController = loader.getController();

            driverFormController.btnDrFormBtn.setText("UPDATE");

            driverFormController.setDriverData(
                    driverDto.getId(),
                    driverDto.getName(),
                    driverDto.getAddress(),
                    driverDto.getEmail(),
                    driverDto.getContact(),
                    driverDto.getLicenseNo(),
                    driverDto.getUserName()
            );
            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update Driver Form");
            stage.centerOnScreen();
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void btnADDDrOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DriverForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Driver Form");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setScene(scene); // stage.setScene(new Scene(rootNode));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DeleteDriverForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Delete Driver Form");
        stage.centerOnScreen();
        stage.show();
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
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/CustomerManageForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Customer Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

  /*  @FXML
    void btnSEARCHOnAction(ActionEvent event) {
        FilteredList<DriverTm> filteredData = new FilteredList<>(obList, b -> true);

        txtSearchDr.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(DriverTm -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if(DriverTm.getId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(DriverTm.getName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(DriverTm.getAdgetdress().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(CustomerTm.getEmail().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(CustomerTm.getContact().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else
                    return false;
            });
        });

        SortedList<CustomerTm> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }*/

    @FXML
    void btnUPDATEOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/UpdateDriverForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Update Driver Form");
        stage.centerOnScreen();
        stage.show();
    }

}
