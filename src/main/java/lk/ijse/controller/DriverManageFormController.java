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
import lk.ijse.dto.DriverDto;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.DriverTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.DriverModel;
import lk.ijse.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DriverManageFormController {
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colAvailability;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colDrId;

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
    private TextField txtSearchDr;

    @FXML
    private TableView<DriverTm> tableView;

    private Integer index;

    private final ObservableList<DriverTm> obList = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        loadAllDrivers();
    }

    private void setCellValueFactory(){
        colDrId.setCellValueFactory(new PropertyValueFactory<>("drId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colLicenseNo.setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("UpdateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("DeleteButton"));
    }

    public void loadAllDrivers(){

        obList.clear();

        var model = new DriverModel();

        try {
            List<DriverDto> dtoList = model.getAllDrivers();

            for(DriverDto dto : dtoList){
                Button updateButton = new Button("Update");
                Button deleteButton = new Button("Delete");

                updateButton.setStyle("-fx-background-color: white; -fx-text-fill: green; -fx-font-weight: bold;");
                deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: #d71010; -fx-font-weight: bold;");

                updateButton.setOnAction(event -> openDriverPopup(dto));
                deleteButton.setOnAction(event -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        deleteDriver(dto.getUserName());
                    }
                }); //*********** getId()
                obList.add(
                        new DriverTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getEmail(),
                                dto.getContact(),
                                dto.getLicenseNo(),
                                dto.getUserName(),
                                dto.getAvailability(),
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

    private void deleteDriver(String userName) {
        var model = new DriverModel();

        try {
            boolean b = model.deleteDriver(userName);

            if(b){
                loadAllDrivers();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                Parent rootNode = loader.load();

                ConfirmationController confirmationController = loader.getController();

                confirmationController.lblConfirm.setText("Driver deleted successfully");

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

    private void openDriverPopup(DriverDto driverDto){
        var model = new UserModel();

        try {
            String pwd = model.getPassword(driverDto.getUserName());

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
                    driverDto.getUserName(),
                    pwd
            );
            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update Driver Form");
            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void btnADDDrOnAction(ActionEvent event) throws IOException {
        var model = new DriverModel();

        try {
            String drId = model.generateNextDrId();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DriverForm.fxml"));

            Parent rootNode = loader.load();

            DriverFormController driverFormController = loader.getController();

            driverFormController.setData(
                    drId
            );

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add Driver Form");
            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
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

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        FilteredList<DriverTm> filteredData = new FilteredList<>(obList, b -> true);

        txtSearchDr.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(DriverTm -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if(DriverTm.getDrId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(DriverTm.getName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(DriverTm.getAddress().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(DriverTm.getEmail().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(DriverTm.getContact().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else
                    return false;
            });
        });

        SortedList<DriverTm> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {
        loadAllDrivers();
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
