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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.tm.CarTm;
import lk.ijse.model.CarModel;
import lk.ijse.model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
    private TableColumn<?, ?> colAvailability;

    @FXML
    private TableColumn<?, ?> colCurrentMileage;

    @FXML
    private TableColumn<?, ?> colKmOneDay;

    @FXML
    private TableColumn<?, ?> colPriceExtraKm;

    @FXML
    private TableColumn<?, ?> colPriceOneDay;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<CarTm> tableView;

    @FXML
    private TextField txtSearchCar;

    public final ObservableList<CarTm> obListCar = FXCollections.observableArrayList();

    ObservableList<String> obList = FXCollections.observableArrayList();


    public void initialize(){
        setCellValueFactory();
        loadAllCars();
    }

    private void setCellValueFactory(){
        colCarNo.setCellValueFactory(new PropertyValueFactory<>("carNo"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("Availability"));
        colCurrentMileage.setCellValueFactory(new PropertyValueFactory<>("CurrentMileage"));
        colKmOneDay.setCellValueFactory(new PropertyValueFactory<>("KmOneDay"));
        colPriceOneDay.setCellValueFactory(new PropertyValueFactory<>("PriceOneDay"));
        colPriceExtraKm.setCellValueFactory(new PropertyValueFactory<>("PriceExtraKm"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("UpdateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("DeleteButton"));
    }

    public void loadAllCars(){
        obListCar.clear();

        var model = new CarModel();

        try {
            List<CarDto> dtoList = model.getAllCars();

            for (CarDto dto : dtoList){
                Button updateButton = new Button("Update");
                Button deleteButton = new Button("Delete");

                updateButton.setStyle("-fx-background-color: white; -fx-text-fill: green; -fx-font-weight: bold;");
                deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: #d71010; -fx-font-weight: bold;");

                updateButton.setOnAction(event -> openCarPopup(dto));
                deleteButton.setOnAction(event -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        deleteCar(dto.getCarNo());
                    }
                });
                obListCar.add(
                        new CarTm(
                                dto.getCarNo(),
                                dto.getBrand(),
                                dto.getAvailability(),
                                dto.getCurrentMileage(),
                                dto.getKmOneDay(),
                                dto.getPriceOneDay(),
                                dto.getPriceExtraKm(),
                                updateButton,
                                deleteButton
                        )
                );
            }
            tableView.setItems(obListCar);
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                Parent rootNode = loader.load();

                ConfirmationController confirmationController = loader.getController();

                confirmationController.lblConfirm.setText("Car deleted successfully");

                Scene scene = new Scene(rootNode);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.centerOnScreen();
                stage.show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {
        loadAllCars();
    }

    private void openCarPopup(CarDto carDto) {
        obList.clear();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CarForm.fxml"));

            Parent rootNode = loader.load();

            CarFormController carFormController = loader.getController();

            carFormController.btnCarForm.setText("UPDATE");

            carFormController.setCarData(
                    carDto.getCarNo(),
                    carDto.getBrand(),
                    carDto.getCurrentMileage(),
                    carDto.getPriceOneDay(),
                    carDto.getKmOneDay(),
                    carDto.getPriceExtraKm()
            );

            loadAvailability();

            carFormController.setComboData(
                   obList
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

    private void loadAvailability() {
        obList.add(0, "YES");
        obList.add(1, "NO");
        obList.add(2, "MAINTAIN");
    }

    @FXML
    void btnADDCarOnAction(ActionEvent event) throws IOException {
        obList.clear();

        var model = new CarModel();

        try {
            String carNo = model.generateNextCarNo();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CarForm.fxml"));

            Parent rootNode = loader.load();

            CarFormController carFormController = loader.getController();

            carFormController.setData(
                    carNo
            );

            loadAvailability();

            carFormController.setComboData(
                    obList
            );

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add Car Form");
            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
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
    void txtSEARCHOnAction(ActionEvent event) {
        FilteredList<CarTm> filteredData = new FilteredList<>(obListCar, b -> true);

        txtSearchCar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(CarTm -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if(CarTm.getCarNo().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(CarTm.getBrand().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else
                    return false;
            });
        });

        SortedList<CarTm> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }

    @FXML
    void btnBookingOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/BookingForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Booking Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
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

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/PaymentForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Payment Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnReportOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/ReportForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Report Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnSalaryOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/SalaryForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Salary Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnAdminOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/VerifySuperAdmin.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Admin Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
