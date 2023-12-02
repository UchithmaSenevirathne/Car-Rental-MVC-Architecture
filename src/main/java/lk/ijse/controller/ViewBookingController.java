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
import lk.ijse.dto.CompleteDTO;
import lk.ijse.dto.PendingDTO;
import lk.ijse.dto.tm.CarTm;
import lk.ijse.dto.tm.CompleteTm;
import lk.ijse.dto.tm.PendingTm;
import lk.ijse.model.BookingModel;
import lk.ijse.model.CarModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ViewBookingController {
    @FXML
    private TableColumn<?, ?> colAdvancePending;

    @FXML
    private TableColumn<?, ?> colBIdPending;

    @FXML
    private TableColumn<?, ?> colCarNoPending;

    @FXML
    private TableColumn<?, ?> colCusIdPending;

    @FXML
    private TableColumn<?, ?> colDaysPending;

    @FXML
    private TableColumn<?, ?> colDeletePending;

    @FXML
    private TableColumn<?, ?> colDrIdPending;

    @FXML
    private TableColumn<?, ?> colPickUpDatePending;

    @FXML
    private TableColumn<?, ?> colUpdatePending;

    @FXML
    private AnchorPane subAnchorPane;

    @FXML
    private TableView<PendingTm> tablePending;

    @FXML
    private TextField txtSearchPending;

    @FXML
    private TableColumn<?, ?> colBIdCom;

    @FXML
    private TableColumn<?, ?> colCusIdCom;

    @FXML
    private TableColumn<?, ?> colPickUpDateCom;

    @FXML
    private TableColumn<?, ?> colDaysCom;

    @FXML
    private TableColumn<?, ?> colTotalPayCom;

    @FXML
    private TableView<CompleteTm> tableCompl;

    @FXML
    private TextField txtSearchCompleted;

    public final ObservableList<PendingTm> obListPend = FXCollections.observableArrayList();

    public final ObservableList<CompleteTm> obListComp = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactoryPending();
        setCellValueFactoryCompleted();
        loadAllPendingBookings();
        loadAllCompletedBookings();
    }

    private void setCellValueFactoryPending(){
        colBIdPending.setCellValueFactory(new PropertyValueFactory<>("bId"));
        colCusIdPending.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colDrIdPending.setCellValueFactory(new PropertyValueFactory<>("drId"));
        colCarNoPending.setCellValueFactory(new PropertyValueFactory<>("carNo"));
        colPickUpDatePending.setCellValueFactory(new PropertyValueFactory<>("pickUpDate"));
        colDaysPending.setCellValueFactory(new PropertyValueFactory<>("days"));
        colAdvancePending.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colUpdatePending.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        colDeletePending.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
    }

   private void setCellValueFactoryCompleted(){
        colBIdCom.setCellValueFactory(new PropertyValueFactory<>("bId"));
        colCusIdCom.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colPickUpDateCom.setCellValueFactory(new PropertyValueFactory<>("pickUpDate"));
        colDaysCom.setCellValueFactory(new PropertyValueFactory<>("days"));
        colTotalPayCom.setCellValueFactory(new PropertyValueFactory<>("totalPayment"));
    }

    public void loadAllPendingBookings() {
        obListPend.clear();

        var model = new BookingModel();

        try {
            List<PendingDTO> dtoList = model.getAllPendings();

            for (PendingDTO dto : dtoList){
                Button updateButton = new Button("Update");
                Button deleteButton = new Button("Delete");

                updateButton.setStyle("-fx-background-color: white; -fx-text-fill: green; -fx-font-weight: bold;");
                deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: #d71010; -fx-font-weight: bold;");

                updateButton.setOnAction(event -> openUpdatePopup(dto));
                deleteButton.setOnAction(event -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        deleteBooking(dto.getBId());
                    }
                });
                obListPend.add(
                        new PendingTm(
                                dto.getBId(),
                                dto.getCusId(),
                                dto.getDrId(),
                                dto.getCarNo(),
                                dto.getPickUpDate(),
                                dto.getDays(),
                                dto.getPayment(),
                                updateButton,
                                deleteButton
                        )
                );
            }
            tablePending.setItems(obListPend);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void deleteBooking(String bId) {
        var model = new BookingModel();

        try {
            boolean b = model.deleteBooking(bId);

            if(b){
                loadAllPendingBookings();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                Parent rootNode = loader.load();

                ConfirmationController confirmationController = loader.getController();

                confirmationController.lblConfirm.setText("Booking deleted successfully");

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

    private void openUpdatePopup(PendingDTO dto) {
        obListPend.clear();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateBooking.fxml"));

            Parent rootNode = loader.load();

            UpdateBookingFormController updateBookingFormController = loader.getController();

            updateBookingFormController.setBookingData(
                    dto.getBId(),
                    dto.getCusId(),
                    dto.getDrId(),
                    dto.getCarNo(),
                    dto.getPickUpDate(),
                    dto.getDays(),
                    dto.getPayment()
            );

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update Booking Form");
            stage.centerOnScreen();
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

   private void loadAllCompletedBookings() {
        obListComp.clear();

        var model = new BookingModel();

        try {
            List<CompleteDTO> dtoList = model.getAllCompletes();

            for (CompleteDTO dto : dtoList){
                obListComp.add(
                        new CompleteTm(
                                dto.getBId(),
                                dto.getCusId(),
                                dto.getPickUpDate(),
                                dto.getDays(),
                                dto.getTotalPayment()
                        )
                );
            }
            tableCompl.setItems(obListComp);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void searchPendingOnAction(ActionEvent event) {
        FilteredList<PendingTm> filteredData = new FilteredList<>(obListPend, b -> true);

        txtSearchPending.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(PendingTm -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                String daysString = String.valueOf(PendingTm.getDays());
                String advanceString = String.valueOf(PendingTm.getPayment());

                if(PendingTm.getBId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(PendingTm.getCusId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(PendingTm.getDrId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(PendingTm.getCarNo().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(PendingTm.getPickUpDate().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(daysString.toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(advanceString.toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else
                    return false;
            });
        });

        SortedList<PendingTm> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tablePending.comparatorProperty());

        tablePending.setItems(sortedData);
    }

    @FXML
    void searchCompletedOnAction(ActionEvent event) {
        FilteredList<CompleteTm> filteredData = new FilteredList<>(obListComp, b -> true);

        txtSearchCompleted.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(CompleteTm -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                String daysString = String.valueOf(CompleteTm.getDays());
                String totalString = String.valueOf(CompleteTm.getTotalPayment());

                if(CompleteTm.getBId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(CompleteTm.getCusId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(CompleteTm.getPickUpDate().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(daysString.toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(totalString.toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else
                    return false;
            });
        });

        SortedList<CompleteTm> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableCompl.comparatorProperty());

        tableCompl.setItems(sortedData);
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookingForm.fxml"));
        AnchorPane booking = loader.load();

        subAnchorPane.getChildren().setAll(booking);
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {
        loadAllPendingBookings();
    }
}
