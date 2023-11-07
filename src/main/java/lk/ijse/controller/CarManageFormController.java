package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import lk.ijse.dto.CarDto;
import lk.ijse.dto.tm.CarTm;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.model.CarModel;

import java.io.IOException;
import java.util.List;

public class CarManageFormController {

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colCarNo;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<CarTm> tableView;

    private final ObservableList<CarTm> obList = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        loadAllCars();
    }

    private void setCellValueFactory(){
        colCarNo.setCellValueFactory(new PropertyValueFactory<>("carNo"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
    }

    private void loadAllCars(){
        var model = new CarModel();

        try {
            List<CarDto> dtoList = model.getAllCars();

            for (CarDto dto : dtoList){
                obList.add(
                        new CarTm(
                                dto.getCarNo(),
                                dto.getBrand()
                        )
                );
            }
            tableView.setItems(obList);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnADDCarOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/AddCarForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Car Form");
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
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/CustomerManageForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Customer Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DeleteCarForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Delete Car Form");
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
    void btnDriverOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DriverManageForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Driver Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnSEARCHOnAction(ActionEvent event) {

    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/UpdateCarForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Update Car Form");
        stage.centerOnScreen();
        stage.show();
    }
}
