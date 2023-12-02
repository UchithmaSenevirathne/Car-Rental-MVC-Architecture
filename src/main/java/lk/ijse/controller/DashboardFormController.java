package lk.ijse.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.CarOutDto;
import lk.ijse.dto.DriverInTimeDto;
import lk.ijse.dto.tm.CarOutTM;
import lk.ijse.dto.tm.CarTm;
import lk.ijse.dto.tm.DriverInTimeTM;
import lk.ijse.model.BookingModel;
import lk.ijse.model.CarModel;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.DriverModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable{

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblBookings;

    @FXML
    private Label lblEarnings;

    @FXML
    private Label lbltravellers;

    @FXML
    private TableColumn<?, ?> colCarNo;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableView<CarOutTM> tableView;

    @FXML
    private TableColumn<?, ?> colDrName;

    @FXML
    private TableColumn<?, ?> colLoginTime;

    @FXML
    private TableView<DriverInTimeTM> tableViewLogin;

    public final ObservableList<CarOutTM> obListCar = FXCollections.observableArrayList();

    public final ObservableList<DriverInTimeTM> obListDrLog = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactoryCar();
        setCellValueFactoryLog();
        loadAllCars();
        loadAllDrIn();
        setValuesLables();
    }

    private void loadAllDrIn() {
        obListDrLog.clear();

        var model = new DriverModel();

        try {
            String date = String.valueOf(LocalDate.now());

            List<DriverInTimeDto> dtoList = model.gerDrInTime(date);

            for (DriverInTimeDto dto : dtoList){
                obListDrLog.add(
                        new DriverInTimeTM(
                                dto.getName(),
                                dto.getTime()
                        )
                );
            }
            tableViewLogin.setItems(obListDrLog);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactoryLog() {
        colDrName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLoginTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    private void setValuesLables() {
        var modelBook = new BookingModel();
        var modelCus = new CustomerModel();

        try{
            int countBooking = modelBook.getCountBooking();

            int countCustomers = modelCus.getCountCus();

            lblBookings.setText(String.valueOf(countBooking));
            lbltravellers.setText(String.valueOf(countCustomers));

            lblBookings.setStyle("-fx-text-fill: #1abc9c; -fx-font-weight: bold; -fx-font-size: 19px");
            lbltravellers.setStyle("-fx-text-fill: #fd9644; -fx-font-weight: bold; -fx-font-size: 19px");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadAllCars() {
        obListCar.clear();

        var model = new CarModel();

        try {
            List<CarOutDto> dtoList = model.getCarOut();

            for (CarOutDto dto : dtoList){
                obListCar.add(
                        new CarOutTM(
                                dto.getCarNo(),
                                dto.getBrand(),
                                dto.getPickUpDate()
                        )
                );
            }
            tableView.setItems(obListCar);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactoryCar() {
        colCarNo.setCellValueFactory(new PropertyValueFactory<>("carNo"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("pickUpDate"));
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {

        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/CustomerManageForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Customer Manage Form");
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
    void btnBookingOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/BookingForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Booking Manage Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnCarOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/CarManageForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Car Manage Form");
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
    void btnAdminOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/VerifySuperAdmin.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Admin Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}


