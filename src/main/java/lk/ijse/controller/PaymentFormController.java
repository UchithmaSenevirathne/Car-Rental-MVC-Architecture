package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;
import lk.ijse.dto.tm.BookTm;
import lk.ijse.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PaymentFormController {
    private final ObservableList<BookTm> obList = FXCollections.observableArrayList();
    //ObservableList<BookTm> obList = FXCollections.observableArrayList();

    private final CustomerModel customerModel = new CustomerModel();

    private final DriverModel driverModel = new DriverModel();

    private final CarModel carModel = new CarModel();

    private final BookingDetailModel bookingDetailModel = new BookingDetailModel();

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
    private ComboBox<String> txtRentId;

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

    private double totalPay = 0;

    public void initialize() {

        setCellValueFactory();
        loadAllBookingIds();
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

    private void loadAllBookingIds(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<BookingDetailDTO> bookDTOList = bookingDetailModel.getAllBookings();

            for (BookingDetailDTO bookDTO : bookDTOList) {
                obList.add(bookDTO.getBId());
            }
            txtRentId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        double extraKm = Integer.parseInt(txtExtraKm.getText());

        //
        CarDto dto = carModel.searchCar(txtCarId.getText());
        double priceExtraKm = dto.getPriceExtraKm();
        //

        double driverCost = Double.parseDouble(txtDrCost.getText());

        double total = ((priceOneDay * days) + (extraKm * priceExtraKm) + driverCost);

        String bId = txtRentId.getValue();
        String carNo = txtCarId.getText();

        var dtoOneCarPay =  new OneCarPayDTO(bId,carNo,extraKm,driverCost,total);

        System.out.println(dtoOneCarPay);

        try {

            boolean isSaved = OneCarPayModel.savePayment(dtoOneCarPay);

            if (isSaved){
                totalPayment(total);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void totalPayment(double total) {
        totalPay += total;

        txtTotal.setText(String.valueOf(totalPay));

        clearTxt();
    }

    private void clearTxt() {
        txtCarId.setText("");
        txtBrand.setText("");
        txtPriceOneDay.setText("");
        txtExtraKm.setText("");
        txtDrName.setText("");
        txtDrId.setText("");
        txtDrCost.setText("");
    }

    @FXML
    void btnCheckOutOnAction(ActionEvent event){
        String bId = txtRentId.getValue();
        double totalPayment = Double.parseDouble(txtTotal.getText());
        String pickUpDate = txtDate.getText();

        String carNo = txtCarId.getText();
        String drId = txtDrId.getText();

        var bookingDetailDto = new BookingDetailDTO(
             bId,
             carNo,
             drId
        );

        try {
            boolean isSaved = PaymentModel.savePayment(bId, totalPayment, pickUpDate,bookingDetailDto);

            if(isSaved){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                Parent rootNode = loader.load();

                ConfirmationController confirmationController = loader.getController();

                confirmationController.lblConfirm.setText("Payment completed successfully");

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

    private void clearFields() {
        txtRentId.setValue("");
        txtDate.setText("");
        txtCusId.setText("");
        txtName.setText("");
        txtContact.setText("");
        txtAddress.setText("");
        txtCarId.setText("");
        txtBrand.setText("");
        txtPriceOneDay.setText("");
        txtExtraKm.setText("");
        txtDrName.setText("");
        txtDrId.setText("");
        txtDrCost.setText("");
        txtTotal.setText("");
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String bId = txtRentId.getValue();

        try {

            List<PaymentDetailDTO> dto = PaymentModel.searchPaymentDetail(bId);

            for(PaymentDetailDTO dto1 : dto) {
                txtRentId.setValue(dto1.getBId());
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
    void btnGenerateBillOnAction(ActionEvent event) {
        String bId = txtRentId.getValue();
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/PayBill.jrxml");

            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText("select c.cusId,c.name,c.address,c.email,c.contact,b.bId,cr.brand,cr.priceOneDay,b.days,pc.extraKm,cr.priceExtraKm,pc.driverCost,pc.subTotal,p.totalPayment from customer c left join booking b on c.cusId = b.cusId left join bookingDetail bd on b.bId = bd.bId left join payment p on b.bId = p.bId left join driver d on bd.drId = d.drId left join car cr on bd.carNo = cr.carNo left join oneCarPayment pc on cr.carNo = pc.carNo where b.bId = '"+ bId +"';");
            jasperDesign.setQuery(jrDesignQuery);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperReport, //compiled report
                            null,
                            DbConnection.getInstance().getConnection() //database connection
                    );

            JFrame frame = new JFrame("Jasper Report Viewer");
            JRViewer viewer = new JRViewer(jasperPrint);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(viewer);
            frame.setSize(new Dimension(1200, 800));
            frame.setVisible(true);

            clearFields();

        }catch (JRException | SQLException e){
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
