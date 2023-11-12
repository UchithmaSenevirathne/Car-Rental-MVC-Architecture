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
import lk.ijse.dto.CarDto;
import lk.ijse.dto.tm.CarTm;
import lk.ijse.model.CarModel;
import lk.ijse.model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CarManageFormController {

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colCarNo;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colUpdate;


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
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("UpdateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("DeleteButton"));
    }

    private void loadAllCars(){
        obList.clear();

        var model = new CarModel();

        try {
            List<CarDto> dtoList = model.getAllCars();

            for (CarDto dto : dtoList){
                Button updateButton = new Button("Update");
                Button deleteButton = new Button("Delete");

                updateButton.setOnAction(event -> openCarPopup(dto));
                deleteButton.setOnAction(event -> deleteCar(dto.getCarNo()));
                obList.add(
                        new CarTm(
                                dto.getCarNo(),
                                dto.getBrand(),
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

    private void deleteCar(String carNo) {
        var model = new CarModel();

        try {
            boolean b = model.deleteCar(carNo);

            if(b){
                loadAllCars();
                new Alert(Alert.AlertType.CONFIRMATION, "car deleted!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void openCarPopup(CarDto carDto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CarForm.fxml"));

            Parent rootNode = loader.load();

            CarFormController carFormController = loader.getController();

            carFormController.btnCarForm.setText("UPDATE");

            carFormController.setCarData(
                    carDto.getCarNo(),
                    carDto.getBrand()
            );

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update Car Form");
            stage.centerOnScreen();
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void btnADDCarOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/CarForm.fxml"));

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
