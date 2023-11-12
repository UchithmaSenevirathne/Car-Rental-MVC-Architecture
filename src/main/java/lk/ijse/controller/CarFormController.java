package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.CarDto;
import lk.ijse.model.CarModel;

public class CarFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtCarBrand;

    @FXML
    private TextField txtCarNo;

    @FXML
    public Button btnCarForm;

    @FXML
    private Label lblCarForm;

    Stage stage;

    @FXML
    void btnCancelCarOnAction(ActionEvent event) {
        stage = (Stage) rootNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnCarOnAction(ActionEvent event) {
        String carNo = txtCarNo.getText();
        String brand = txtCarBrand.getText();
        String availability = "yes";
        double currentMilage = 0.0;
        double kmOneDay = 0.0;
        double priceOneDay = 0.0;
        double priceExtraKm = 0.0;

        var dto = new CarDto(carNo, brand, availability, currentMilage, kmOneDay, priceOneDay, priceExtraKm);

        var model = new CarModel();

        try{
            if(btnCarForm.getText().equals("UPDATE")){
                boolean isUpdated = model.updateCar(dto);
                if(isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "car updated!!").show();
                    clearFields();
                }
            }else if(btnCarForm.getText().equals("Save")) {
                boolean isSaved = model.saveCar(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "car saved!").show();
                    clearFields();
                }
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtCarNo.setText("");
        txtCarBrand.setText("");
    }

    public void setCarData(String carNo, String brand) {
        txtCarNo.setText(carNo);
        txtCarBrand.setText(brand);
    }
}
