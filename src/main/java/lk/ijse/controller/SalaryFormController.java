package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.tm.DriverTm;
import lk.ijse.dto.tm.SalDriverTm;
import lk.ijse.model.DriverModel;
import lk.ijse.model.SalaryModel;

import java.io.IOException;
import java.util.List;

public class SalaryFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colAvailability;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDrId;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colLicenseNo;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private TableView<SalDriverTm> tableView;

    @FXML
    private TextField txtSearchDr;

    private Integer index;

    private final ObservableList<SalDriverTm> obList = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        //loadAllDrivers();
        txtSearchDr.textProperty().addListener((observable, oldValue, newValue) -> {
           searchDrivers(newValue);
        });
    }

    private void setCellValueFactory(){
        colDrId.setCellValueFactory(new PropertyValueFactory<>("drId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colLicenseNo.setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }

    @FXML
    void btnADDSalOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/AddSalaryForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Salary Form");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnViewAllOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/ViewSalary.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("View Salary Form");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void txtSEARCHOnAction(ActionEvent event) {
        String search = txtSearchDr.getText();
        System.out.println("search "+search);
        searchDrivers(search);
    }

    private void searchDrivers(String search) {
        obList.clear();

        var model = new SalaryModel();

        try {
            if (model != null) {
                List<DriverDto> dtoList = model.getAllDrivers(search);

                if (dtoList != null) {
                    for (DriverDto dto : dtoList) {
                        obList.add(new SalDriverTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getEmail(),
                                dto.getContact(),
                                dto.getLicenseNo(),
                                dto.getUserName(),
                                dto.getAvailability()
                        ));
                    }
                    tableView.setItems(obList);
                } else {
                    new Alert(Alert.AlertType.ERROR, "ERROR : Driver data is null").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "SalaryModel is not initialized").show();
            }

        } catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error\", \"An error occurred while fetching driver data.").show();
        }
    }

    @FXML
    void setData(MouseEvent event) {
        index = tableView.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }

        String drId = colDrId.getCellData(index).toString();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddSalaryForm.fxml"));

            Parent rootNode = loader.load();

            AddSalaryFormController addSalaryFormController = loader.getController();

            addSalaryFormController.setSalaryData(
                    drId
            );

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add Salary Form");
            stage.centerOnScreen();
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
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
    void btnAdminOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/VerifySuperAdmin.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Admin Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
