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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
    private TableColumn<CustomerTm, String> colCusId;

    @FXML
    private TableColumn<CustomerTm, String> colName;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colUpdate;

    @FXML
    private TableView<CustomerTm> tableView;

    @FXML
    private TextField txtSearchCus;


    private final ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory(){
        colCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
    }

    public void loadAllCustomers(){
        obList.clear();

        var model = new CustomerModel();

        //ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try{
            List<CustomerDto> dtoList = model.getAllCustomers();

            for(CustomerDto dto : dtoList){
                Button updateButton = new Button("Update");
                Button deleteButton = new Button("Delete");

                updateButton.setStyle("-fx-background-color: white; -fx-text-fill: green; -fx-font-weight: bold;");
                deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: #d71010; -fx-font-weight: bold;");

                updateButton.setOnAction(event -> openCustomerPopup(dto));
                deleteButton.setOnAction(event -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        deleteCustomer(dto.getId());
                    }
                });
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
        //CustomerFormController cusForm = new CustomerFormController(this);
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                Parent rootNode = loader.load();

                ConfirmationController confirmationController = loader.getController();

                confirmationController.lblConfirm.setText("Customer deleted successfully");

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
        var model = new CustomerModel();

        try {
            String cusId = model.generateNextCusId();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerForm.fxml"));

            Parent rootNode = loader.load();

            CustomerFormController customerFormController = loader.getController();

            customerFormController.setData(
                    cusId
            );

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add Customer Form");
            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void txtSEARCHOnAction(ActionEvent event) {
        FilteredList<CustomerTm> filteredData = new FilteredList<>(obList, b -> true);

        txtSearchCus.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(CustomerTm -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if(CustomerTm.getCusId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(CustomerTm.getName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(CustomerTm.getAddress().toLowerCase().indexOf(searchKeyword) > -1){
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
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {
        loadAllCustomers();
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
