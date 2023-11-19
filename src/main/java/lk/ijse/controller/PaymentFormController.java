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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.*;
import lk.ijse.dto.tm.BookTm;
import lk.ijse.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PaymentFormController {
    private final ObservableList<BookTm> obList = FXCollections.observableArrayList();
    //ObservableList<BookTm> obList = FXCollections.observableArrayList();

    private final CustomerModel customerModel = new CustomerModel();

    private final DriverModel driverModel = new DriverModel();

    private final CarModel carModel = new CarModel();

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colCarId;

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colDays;

    @FXML
    private TableColumn<?, ?> colDrId;

    @FXML
    private TableColumn<?, ?> colPickUpDate;

    @FXML
    private TableColumn<?, ?> colRentId;

    @FXML
    private TextField txtRentId;

    @FXML
    private TableView<BookTm> tableView;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtCarId;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDrCost;

    @FXML
    private TextField txtDrId;

    @FXML
    private TextField txtDrName;

    @FXML
    private TextField txtExtraKm;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPriceOneDay;

    @FXML
    private TextField txtTotal;

    Integer index;

    private int totalPay = 0;

    public void initialize() {
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colRentId.setCellValueFactory(new PropertyValueFactory<>("bId"));
        colCarId.setCellValueFactory(new PropertyValueFactory<>("carNo"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colDrId.setCellValueFactory(new PropertyValueFactory<>("drId"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colPickUpDate.setCellValueFactory(new PropertyValueFactory<>("pickUpDate"));
        colDays.setCellValueFactory(new PropertyValueFactory<>("days"));
    }

    @FXML
    void setDetail(MouseEvent event) throws SQLException {
        index = tableView.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }

        txtCarId.setText(colCarId.getCellData(index).toString());
        txtBrand.setText(colBrand.getCellData(index).toString());
        txtDrId.setText(colDrId.getCellData(index).toString());

        CarDto dto = carModel.searchCar(txtCarId.getText());
        txtPriceOneDay.setText(String.valueOf(dto.getPriceOneDay()));

        DriverDto dto1 = driverModel.searchDriver(txtDrId.getText());
        txtDrName.setText(dto1.getName());
    }

    @FXML
    void btnAddPaymentOnAction(ActionEvent event) throws SQLException {
        //clearTxt();

        double priceOneDay = Double.parseDouble(txtPriceOneDay.getText());

        //
        int index = tableView.getSelectionModel().getSelectedIndex();

        int days = Integer.parseInt(colDays.getCellData(index).toString());
        System.out.println("days....." + days);
        //

        int extraKm = Integer.parseInt(txtExtraKm.getText());

        //
        CarDto dto = carModel.searchCar(txtCarId.getText());
        double priceExtraKm = dto.getPriceExtraKm();
        //

        double driverCost = Double.parseDouble(txtDrCost.getText());

        double total = ((priceOneDay * days) + (extraKm * priceExtraKm) + driverCost);

        totalPayment(total);

    }

    private void totalPayment(double total) {
        totalPay += total;

        txtTotal.setText(String.valueOf(totalPay));

        clearTxt();
    }

    private void clearTxt() {
        txtExtraKm.setText("");
        txtDrCost.setText("");
    }

    @FXML
    void btnCheckOutOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String bId = txtRentId.getText();

        try {
            //PaymentDetailDTO dto = PaymentModel.searchPaymentDetail(bId);

            List<PaymentDetailDTO> dto = PaymentModel.searchPaymentDetail(bId);

            for(PaymentDetailDTO dto1 : dto) {
                txtRentId.setText(dto1.getBId());
                txtDate.setText(String.valueOf(dto1.getPickUpDate()));
                txtCusId.setText(dto1.getCusId());

                String cusId = txtCusId.getText();
                CustomerDto dtoCus = customerModel.searchCustomer(cusId);

                txtName.setText(dtoCus.getName());
                txtContact.setText(dtoCus.getContact());
                txtAddress.setText(dtoCus.getAddress());
            }
            addToTable(dto);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addToTable(List<PaymentDetailDTO> dto) throws SQLException {
        //ObservableList<BookTm> obList = FXCollections.observableArrayList();

        /*String bId = dto.getBId();
        String carNo = dto.getCarNo();
        CarDto dtoCar = carModel.searchCar(carNo);
        String brand = dtoCar.getBrand();
        String drId = dto.getDrId();
        String cusId = dto.getCusId();
        Date pickUpDate = java.sql.Date.valueOf(txtDate.getText());
        int days = dto.getDays();*/

        for (PaymentDetailDTO dto1 : dto){
            CarDto dtoCar = carModel.searchCar(dto1.getCarNo());
            String brand = dtoCar.getBrand();
            obList.add(new BookTm(
                    dto1.getBId(),
                    dto1.getCarNo(),
                    brand,
                    dto1.getDrId(),
                    dto1.getCusId(),
                    dto1.getPickUpDate(),
                    dto1.getDays()
            ));
        }
        System.out.println("++++++++++");
        System.out.println(obList);

        tableView.setItems(obList);

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
        stage.setTitle("Cars Manage Form");
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
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Login Form");
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
}
